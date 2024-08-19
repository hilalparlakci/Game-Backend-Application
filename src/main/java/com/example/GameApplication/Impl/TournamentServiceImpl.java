package com.example.GameApplication.Impl;

import com.example.GameApplication.dto.*;
import com.example.GameApplication.model.*;
import com.example.GameApplication.repo.TournamentGroupMemberRepository;
import com.example.GameApplication.repo.TournamentGroupRepository;
import com.example.GameApplication.repo.TournamentRepository;
import com.example.GameApplication.repo.UserRepository;
import com.example.GameApplication.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;
    private final TournamentGroupRepository tournamentGroupRepository;
    private final TournamentGroupMemberRepository tournamentGroupMemberRepository;
    @Override
    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    public void startNewTournament() {
        LocalDate today = LocalDate.now();
        Tournament tournament = new Tournament(today);
    }

    @Override
    public Tournament getActiveTournament() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            return tournamentRepository.findByDate(today)
                    .orElseThrow(() -> new RuntimeException("No active tournament found for today"));
        } else {
            throw new RuntimeException("No active tournament at the moment");
        }
    }

    @Override
    public boolean isUserInActiveTournament(Long userId) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        if (now.isAfter(startTime) && now.isBefore(endTime)) {
            return tournamentGroupMemberRepository.existsByUserIdAndTournamentGroup_Tournament_Date(userId, today);
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void updateTournamentGroupStatus(Long tournamentGroupId) {
        TournamentGroup group = tournamentGroupRepository.findById(tournamentGroupId)
                .orElseThrow(() -> new RuntimeException("Tournament Group not found"));

        long memberCount = tournamentGroupMemberRepository.countByTournamentGroup(group);

        if (memberCount >= 5) {
            group.setStarted(true);
        } else {
            group.setStarted(false);
        }

        tournamentGroupRepository.save(group);
    }

    @Override
    @Transactional
    public EnterTournamentResponse enterTournament(EnterTournamentRequest request) {
        Long userId = request.getId();
        if (userId == null) {
            throw new RuntimeException("The given id must not be null");
        }

        User user = userRepository.findById(userId)
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

        List<TournamentGroup> tournamentGroups = tournamentGroupRepository.findByTournament(tournament);

        TournamentGroup selectedGroup = null;
        Country userCountry = user.getCountry();

        for (TournamentGroup group : tournamentGroups) {
            List<TournamentGroupMember> membersInGroup = tournamentGroupMemberRepository.findByTournamentGroup(group);

            boolean hasCountryFromUser = membersInGroup.stream()
                    .map(TournamentGroupMember::getUser)
                    .filter(Objects::nonNull)
                    .map(User::getCountry)
                    .anyMatch(country -> country.equals(userCountry));

            if (!hasCountryFromUser) {
                selectedGroup = group;
                break;
            }
        }

        if (selectedGroup == null) {
            selectedGroup = new TournamentGroup();
            selectedGroup.setTournament(tournament);
            tournamentGroupRepository.save(selectedGroup);
        }

        TournamentGroupMember groupMember = new TournamentGroupMember();
        groupMember.setUserId(userId);
        groupMember.setTournamentGroupId(selectedGroup.getTournamentGroupId());
        groupMember.setScore(0);
        tournamentGroupMemberRepository.save(groupMember);

        // Update the status of the tournament group based on member count
        updateTournamentGroupStatus(selectedGroup.getTournamentGroupId());

        List<TournamentGroupMember> groupMembers = tournamentGroupMemberRepository.findByTournamentGroup(selectedGroup);

        List<TournamentGroupMemberDTO> groupMemberDTOs = groupMembers.stream()
                .map(member -> {
                    User memberUser = member.getUser();
                    String countryName = (memberUser != null && memberUser.getCountry() != null) ? memberUser.getCountry().getName() : "Unknown";
                    return new TournamentGroupMemberDTO(
                            member.getUserId(),
                            member.getScore(),
                            countryName
                    );
                })
                .collect(Collectors.toList());

        return new EnterTournamentResponse(tournament.getTournamentId(), groupMemberDTOs);
    }

}
