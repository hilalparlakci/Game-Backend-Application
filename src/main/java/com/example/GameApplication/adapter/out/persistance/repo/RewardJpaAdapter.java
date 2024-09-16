package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.RewardEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.UserEntity;
import com.example.GameApplication.application.domain.model.Reward;
import com.example.GameApplication.application.port.out.RewardPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RewardJpaAdapter implements RewardPersistencePort {
    private final RewardRepository rewardRepository;

    @Override
    public Reward save(Reward reward) {
        RewardEntity rewardEntity = rewardRepository.save(new RewardEntity(reward));
        return rewardEntity.toModel();
    }

    @Override
    public Optional<Reward> findByTournamentGroupMemberId(Long tournamentGroupMemberId) {
        return rewardRepository.findByTournamentGroupMemberId(tournamentGroupMemberId)
                .map(RewardEntity::toModel);
    }

}
