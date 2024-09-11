package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.Tournament;

import java.time.LocalDate;
import java.util.Optional;

public interface TournamentPersistencePort {
    Optional<Tournament> findByDate(LocalDate date);
    Tournament save(Tournament tournament);
}
