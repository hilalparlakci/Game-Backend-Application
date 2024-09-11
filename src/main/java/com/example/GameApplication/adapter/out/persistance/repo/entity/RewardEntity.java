package com.example.GameApplication.adapter.out.persistance.repo.entity;

import com.example.GameApplication.application.domain.model.Reward;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Table(name = "reward")
public class RewardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long rewardId;

    @Column(name = "reward_amount")
    private Long rewardAmount;

    @Column(name = "is_claimed")
    private boolean isClaimed;

    @OneToOne(mappedBy = "reward")
    private TournamentGroupMemberEntity tournamentGroupMember;

    public RewardEntity() {
        this.rewardAmount = 0L;
        this.isClaimed = false;

    }

    public Reward toModel(RewardEntity entity) {

        return new Reward(
                entity.getRewardId(),
                entity.getRewardAmount(),
                entity.isClaimed()
        );
    }

    public RewardEntity(Reward model) {
        this.setRewardId(model.getRewardId());
        this.setRewardAmount(model.getRewardAmount());
        this.setClaimed(model.isClaimed());

    }

}
