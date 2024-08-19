package com.example.GameApplication.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "reward")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long rewardId;

    @Column(name = "reward_amount")
    private Long rewardAmount;

    @Column(name = "is_claimed")
    private boolean isClaimed;

    @OneToOne(mappedBy = "reward")
    private TournamentGroupMember tournamentGroupMember;

}
