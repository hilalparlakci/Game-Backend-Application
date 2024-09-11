package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentEntity;
import com.example.GameApplication.application.domain.model.Tournament;
import com.example.GameApplication.application.port.out.TournamentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TournamentJpaAdapter implements TournamentPersistencePort {
    private final TournamentRepository tournamentRepository;

    @Override
    public Optional<Tournament> findByDate(LocalDate date) {
        return tournamentRepository.findByDate(date)
                .map(TournamentEntity::toModel);
    }

    @Override
    public Tournament save(Tournament tournament) {
        return tournamentRepository.save(new TournamentEntity(tournament)).toModel();
    }

}
