package com.example.GameApplication.application.domain.model;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    private Long tournamentId;
    private LocalDate date;

    public Tournament(LocalDate date) {
        this.date = date;
    }

}