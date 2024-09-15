package com.example.GameApplication.application.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reward {
    private Long rewardId;
    private Long rewardAmount;
    private boolean isClaimed;
    private Long tournamentGroupMemberId;
    TournamentGroupMember tournamentGroupMember;
}
