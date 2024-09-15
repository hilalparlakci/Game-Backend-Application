package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.TournamentGroup;
import com.example.GameApplication.application.domain.model.TournamentGroupMember;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TournamentGroupMemberPersistencePort {
    boolean existsByUserIdAndTournamentGroup_Tournament_Date(Long userId, LocalDate date);

    long countMembersByTournamentGroup(TournamentGroup group);

    TournamentGroupMember save(TournamentGroupMember groupMember);

    List<TournamentGroupMember> findByTournamentGroupId(Long tournamentGroupId);

    Optional<TournamentGroupMember> findById(Long tournamentGroupMemberId);
}