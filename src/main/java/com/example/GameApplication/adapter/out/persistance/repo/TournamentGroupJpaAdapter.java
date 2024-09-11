package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.CountryEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupEntity;
import com.example.GameApplication.application.domain.model.*;
import com.example.GameApplication.application.port.out.TournamentGroupPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TournamentGroupJpaAdapter implements TournamentGroupPersistencePort {

    private final TournamentGroupRepository tournamentGroupRepository;

    @Override
    public Optional<TournamentGroup> findById(Long id) {
        return tournamentGroupRepository.findById(id)
                .map(TournamentGroupEntity::toModel);
    }

    @Override
    public TournamentGroup save(TournamentGroup group) {
        return tournamentGroupRepository.save(new TournamentGroupEntity(group)).toModel();
    }

    @Override
    public Optional<TournamentGroup> getSuitableTournamentGroup(Tournament tournament, User user) {
        Optional<TournamentGroupEntity> suitableGroupEntity = tournamentGroupRepository
                .findFirstGroupWithoutUserFromCountry(
                        new TournamentEntity(tournament),
                        new CountryEntity(user.getCountry())
                );

        return suitableGroupEntity.map(TournamentGroupEntity::toModel);
    }
}