package com.example.GameApplication.application.domain.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentGroupMember {
    private Long id;
    private Long userId;
    private Long tournamentGroupId;
    private int score;

}
