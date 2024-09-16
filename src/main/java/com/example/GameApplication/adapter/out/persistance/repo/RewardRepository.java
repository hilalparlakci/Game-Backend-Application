package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.RewardEntity;
import com.example.GameApplication.application.domain.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardRepository extends JpaRepository<RewardEntity,Long> {
    Optional<RewardEntity> findByTournamentGroupMemberId(Long tournamentGroupMemberId);

}
