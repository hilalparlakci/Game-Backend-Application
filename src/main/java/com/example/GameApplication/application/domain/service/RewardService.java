package com.example.GameApplication.application.domain.service;

import com.example.GameApplication.application.domain.model.Reward;
import com.example.GameApplication.application.domain.model.TournamentGroupMember;
import com.example.GameApplication.application.port.out.RewardPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RewardService {
    private final RewardPersistencePort rewardPersistencePort;

    public boolean isRewardClaimed(TournamentGroupMember tournamentGroupMember){
        Reward reward = rewardPersistencePort.findByTournamentGroupMemberId(tournamentGroupMember.getId())
                .orElseThrow(() -> new RuntimeException("No reward found for the given tournament group member."));
        return reward.isClaimed();
    }

    public void createRewardForMember(TournamentGroupMember tournamentGroupMember) {
        Reward reward = new Reward();
        reward.setRewardAmount(0L);
        reward.setClaimed(false);
        reward.setTournamentGroupMemberId(tournamentGroupMember.getId());
        reward.setTournamentGroupMember(tournamentGroupMember);

        rewardPersistencePort.save(reward);
    }
}
