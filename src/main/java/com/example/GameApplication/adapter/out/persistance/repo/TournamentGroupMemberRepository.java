package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentGroupMemberRepository extends JpaRepository<TournamentGroupMemberEntity, Long> {
    boolean existsByUserIdAndTournamentGroup_Tournament_Date(Long userId, LocalDate date);
    @Query("SELECT COUNT(tgm) FROM TournamentGroupMemberEntity tgm WHERE tgm.tournamentGroup = :tournamentGroup")
    long countMembersByTournamentGroup(@Param("tournamentGroup") TournamentGroupEntity tournamentGroup);

    @Query("SELECT tgm FROM TournamentGroupMemberEntity tgm WHERE tgm.tournamentGroup.tournamentGroupId = :tournamentGroupId")
    List<TournamentGroupMemberEntity> findByTournamentGroupId(@Param("tournamentGroupId") Long tournamentGroupId);

}
