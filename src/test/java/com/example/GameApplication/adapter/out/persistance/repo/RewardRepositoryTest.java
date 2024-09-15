package com.example.GameApplication.adapter.out.persistance.repo;

import static org.junit.jupiter.api.Assertions.*;

import com.example.GameApplication.adapter.out.persistance.repo.entity.RewardEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RewardRepositoryTest {
    @Autowired
    private RewardRepository rewardRepository;

    @Test
    void testRewardRepositorySave() {
        RewardEntity rewardEntity = new RewardEntity();
        rewardEntity.setRewardAmount(100L);
        rewardEntity.setClaimed(false);

        RewardEntity savedEntity = rewardRepository.save(rewardEntity);
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getRewardId());
    }
}
