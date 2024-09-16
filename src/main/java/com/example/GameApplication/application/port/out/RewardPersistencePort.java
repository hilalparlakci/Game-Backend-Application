package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.Reward;

import java.util.Optional;

public interface RewardPersistencePort {
    Reward save(Reward reward);
    Optional<Reward> findByTournamentGroupMemberId(Long tournamentGroupMemberId);
}
