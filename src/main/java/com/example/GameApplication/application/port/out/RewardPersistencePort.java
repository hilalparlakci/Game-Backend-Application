package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.Reward;

public interface RewardPersistencePort {
    Reward save(Reward reward);
}
