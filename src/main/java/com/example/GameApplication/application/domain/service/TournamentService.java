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
    private final RewardPersistencePort rewardPersistencePort;

    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    public void startNewTournament() {
        LocalDate today = LocalDate.now();
        Tournament tournament = new Tournament(today);
        tournamentPersistencePort.save(tournament);
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

        //group size must be 5
        group.setStarted(memberCount >= 5);
        return tournamentGroupPersistencePort.save(group);

    }

    @Transactional
    public EnterTournamentResponse enterTournament(EnterTournamentRequest request) {
        Long userId = request.getId();
        if (userId == null) {
            throw new RuntimeException("hey no null id entry");
        }

        User user = userPersistencePort.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getLevel() < 20) {
            throw new RuntimeException("User must be at least level 20 to participate");
        }
        if (user.getCoin() < 1000) {
            throw new RuntimeException("User must have at least 1000 coins to participate");
        }

        if (isUserInActiveTournament(userId)) {
            throw new RuntimeException("User is already in an active tournament");
        }

        Tournament tournament = getActiveTournament();
        Optional<TournamentGroup> suitableTournamentGroupOptional = tournamentGroupPersistencePort.getSuitableTournamentGroup(tournament, user);

        TournamentGroup suitableTournamentGroup = suitableTournamentGroupOptional
                .orElseGet(() -> {
                    TournamentGroup newGroup = new TournamentGroup();
                    newGroup.setTournament(tournament);
                    return newGroup;
                });

        suitableTournamentGroup = tournamentGroupPersistencePort.save(suitableTournamentGroup);

        TournamentGroupMember tournamentGroupMember = new TournamentGroupMember();
        tournamentGroupMember.setUserId(user.getId());
        tournamentGroupMember.setTournamentGroupId(suitableTournamentGroup.getTournamentGroupId());
        tournamentGroupMember.setScore(0);

        tournamentGroupMember = tournamentGroupMemberPersistencePort.save(tournamentGroupMember);

        Reward reward = new Reward();
        reward.setRewardAmount(0L);
        reward.setClaimed(false);
        reward.setTournamentGroupMemberId(tournamentGroupMember.getId());
        reward.setTournamentGroupMember(tournamentGroupMember);

        rewardPersistencePort.save(reward);

        suitableTournamentGroup = updateTournamentGroupStatus(suitableTournamentGroup.getTournamentGroupId());

        List<TournamentGroupMember> tournamentGroupMembers = tournamentGroupMemberPersistencePort
                .findByTournamentGroupId(suitableTournamentGroup.getTournamentGroupId());

        List<GroupMember> groupMembers = tournamentGroupMembers.stream()
                .map(member -> userPersistencePort.findById(member.getUserId())
                        .map(tournamentuser -> new GroupMember(member.getUserId(), member.getScore(), tournamentuser.getCountry().getName()))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new EnterTournamentResponse(tournament.getTournamentId(), suitableTournamentGroup.getTournamentGroupId(), groupMembers);
    }
}
