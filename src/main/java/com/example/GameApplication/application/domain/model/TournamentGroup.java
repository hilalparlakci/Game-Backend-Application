package com.example.GameApplication.application.domain.model;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentGroup {

    private Long tournamentGroupId;
    private boolean isStarted;
    private boolean hasEnded;
    private Tournament tournament;

}