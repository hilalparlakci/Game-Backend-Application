package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.Tournament;
import com.example.GameApplication.application.domain.model.TournamentGroup;
import com.example.GameApplication.application.domain.model.User;

import java.util.Optional;

public interface TournamentGroupPersistencePort {
    Optional<TournamentGroup> findById(Long id);

    TournamentGroup save(TournamentGroup group);

    Optional<TournamentGroup> getSuitableTournamentGroup(Tournament tournament, User user);
}