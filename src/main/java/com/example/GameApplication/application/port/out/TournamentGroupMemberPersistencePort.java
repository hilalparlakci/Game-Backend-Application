package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.TournamentGroup;
import com.example.GameApplication.application.domain.model.TournamentGroupMember;

import java.time.LocalDate;
import java.util.List;

public interface TournamentGroupMemberPersistencePort {
    boolean existsByUserIdAndTournamentGroup_Tournament_Date(Long userId, LocalDate date);

    long countMembersByTournamentGroup(TournamentGroup group);

    TournamentGroupMember save(TournamentGroupMember groupMember);

    List<TournamentGroupMember> findByTournamentGroupId(Long tournamentGroupId);
}