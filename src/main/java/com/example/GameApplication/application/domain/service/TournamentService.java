package com.example.GameApplication.application.domain.service;

import com.example.GameApplication.application.domain.model.*;
import com.example.GameApplication.application.domain.dto.request.EnterTournamentRequest;
import com.example.GameApplication.application.domain.dto.response.EnterTournamentResponse;
import com.example.GameApplication.application.port.out.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final UserPersistencePort userPersistencePort;
    private final TournamentPersistencePort tournamentPersistencePort;
    private final TournamentGroupPersistencePort tournamentGroupPersistencePort;
    private final TournamentGroupMemberPersistencePort tournamentGroupMemberPersistencePort;
    private final RewardService rewardService;
    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    public void startNewTournament() {
        LocalDate today = LocalDate.now();
        Tournament tournament = new Tournament(today);
        tournamentPersistencePort.save(tournament);
    }

    @Scheduled(cron = "0 0 20 * * ?", zone = "UTC")
    @Transactional
    public void endActiveTournaments() {
        LocalDate today = LocalDate.now();

        Tournament tournament = tournamentPersistencePort.findByDate(today)
                .orElseThrow(() -> new RuntimeException("No tournament found for today"));

        List<TournamentGroup> tournamentGroups = tournamentGroupPersistencePort
                .findByTournament(tournament);

        tournamentGroups.forEach(group -> {
            if (!group.isHasEnded()) {
                group.setHasEnded(true);
                tournamentGroupPersistencePort.save(group);
            }
        });
    }

    public Tournament getActiveTournament() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            return tournamentPersistencePort.findByDate(today)
                    .orElseThrow(() -> new RuntimeException("No active tournament found for today"));
        } else {
            throw new RuntimeException("No active tournament at the moment");
        }
    }

    public boolean isUserInActiveTournament(Long userId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            return tournamentGroupMemberPersistencePort.existsByUserIdAndTournamentGroup_Tournament_Date(userId, today);
        } else {
            return false;
        }
    }

    @Transactional
    public TournamentGroup updateTournamentGroupStatus(Long tournamentGroupId) {
        TournamentGroup group = tournamentGroupPersistencePort.findById(tournamentGroupId)
                .orElseThrow(() -> new RuntimeException("Tournament Group not found"));

        long memberCount = tournamentGroupMemberPersistencePort.countMembersByTournamentGroup(group);

        group.setStarted(memberCount == 5);  //group size must be 5
        return tournamentGroupPersistencePort.save(group);
    }

    private void validateUserParticipation(User user) {
        Long id = user.getId();

        if (user.getLevel() < 20) {
            throw new RuntimeException("User must be at least level 20 to participate");
        }
        if (user.getCoin() < 1000) {
            throw new RuntimeException("User must have at least 1000 coins to participate");
        }

        if (isUserInActiveTournament(id)) {
            throw new RuntimeException("User is already in an active tournament");
        }

        Optional<TournamentGroupMember> tournamentGroupMemberOpt = tournamentGroupMemberPersistencePort.findMostRecentByUserId(id);

        if (tournamentGroupMemberOpt.isPresent()) {
            TournamentGroupMember tournamentGroupMember = tournamentGroupMemberOpt.get();
            if (!rewardService.isRewardClaimed(tournamentGroupMember)) {
                throw new RuntimeException("The previous tournament reward has not been claimed yet");
            }
        }
    }

    public User fetchValidUser(Long userId) {
        User user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        validateUserParticipation(user);
        return user;
    }

    public TournamentGroup findOrCreateSuitableGroup(Tournament tournament, User user) {
        return tournamentGroupPersistencePort.getSuitableTournamentGroup(tournament, user)
                .orElseGet(() -> {
                    TournamentGroup newGroup = new TournamentGroup();
                    newGroup.setTournament(tournament);
                    return tournamentGroupPersistencePort.save(newGroup);
                });
    }

    public TournamentGroupMember addUserToGroup(TournamentGroup tournamentGroup, User user) {
        TournamentGroupMember tournamentGroupMember = new TournamentGroupMember();
        tournamentGroupMember.setUserId(user.getId());
        tournamentGroupMember.setTournamentGroupId(tournamentGroup.getTournamentGroupId());
        tournamentGroupMember.setScore(0);

        return tournamentGroupMemberPersistencePort.save(tournamentGroupMember);
    }

    public List<GroupMember> fetchGroupMembers(TournamentGroup suitableTournamentGroup) {
        List<TournamentGroupMember> tournamentGroupMembers = tournamentGroupMemberPersistencePort
                .findByTournamentGroupId(suitableTournamentGroup.getTournamentGroupId());

        return tournamentGroupMembers.stream()
                .map(member -> userPersistencePort.findById(member.getUserId())
                        .map(tournamentUser -> new GroupMember(member.getUserId(), member.getScore(), tournamentUser.getCountry().getName()))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Transactional
    public EnterTournamentResponse enterTournament(EnterTournamentRequest request) {
        Long userId = request.getId();
        userService.validateUserId(userId);

        User user = fetchValidUser(userId);
        Tournament tournament = getActiveTournament();

        TournamentGroup suitableTournamentGroup = findOrCreateSuitableGroup(tournament, user);
        TournamentGroupMember tournamentGroupMember = addUserToGroup(suitableTournamentGroup, user);

        rewardService.createRewardForMember(tournamentGroupMember);

        suitableTournamentGroup = updateTournamentGroupStatus(suitableTournamentGroup.getTournamentGroupId());

        List<GroupMember> groupMembers = fetchGroupMembers(suitableTournamentGroup);

        return new EnterTournamentResponse(tournament.getTournamentId(), suitableTournamentGroup.getTournamentGroupId(), groupMembers);
    }

}
