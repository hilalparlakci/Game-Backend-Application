package com.example.GameApplication.repo;

import com.example.GameApplication.model.TournamentGroup;
import com.example.GameApplication.model.TournamentGroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentGroupMemberRepository extends JpaRepository<TournamentGroupMember, Long> {
    boolean existsByUserIdAndTournamentGroup_Tournament_Date(Long userId, LocalDate date);

    List<TournamentGroupMember> findByTournamentGroup(TournamentGroup group);
    long countByTournamentGroup(TournamentGroup tournamentGroup);


}