package com.example.GameApplication.adapter.out.persistance.repo.entity;

import com.example.GameApplication.application.domain.model.Reward;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
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

    @Column(name = "tournament_group_member_id")
    private Long tournamentGroupMemberId;

    @OneToOne
    @JoinColumn(name = "tournament_group_member_id", referencedColumnName = "tournament_group_member_id", insertable = false, updatable = false)
    private TournamentGroupMemberEntity tournamentGroupMember;

    public Reward toModel() {
        return new Reward(
                this.rewardId,
                this.rewardAmount,
                this.isClaimed,
                this.tournamentGroupMemberId,
                this.tournamentGroupMember.toModel()

        );
    }

    public RewardEntity(Reward model) {
        this.rewardId = model.getRewardId();
        this.rewardAmount = model.getRewardAmount();
        this.isClaimed = model.isClaimed();
        this.tournamentGroupMember = new TournamentGroupMemberEntity(model.getTournamentGroupMember());
        tournamentGroupMemberId = model.getTournamentGroupMemberId();
    }
}
