package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.CountryEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TournamentGroupRepository extends JpaRepository<TournamentGroupEntity, Long> {
    @Query("SELECT tg FROM TournamentGroupEntity tg " +
            "WHERE tg.tournament = :tournament " +
            "AND NOT EXISTS (SELECT 1 FROM TournamentGroupMemberEntity tgm " +
            "WHERE tgm.tournamentGroup = tg " +
            "AND tgm.user.country = :country)")
    List<TournamentGroupEntity> findGroupsWithoutUserFromCountry(@Param("tournament") TournamentEntity tournament,
                                                                 @Param("country") CountryEntity country);

}