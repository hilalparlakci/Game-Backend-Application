package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.CountryEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupEntity;
import com.example.GameApplication.application.domain.model.*;
import com.example.GameApplication.application.port.out.TournamentGroupPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<TournamentGroupEntity> groups = tournamentGroupRepository.findGroupsWithoutUserFromCountry(new TournamentEntity(tournament), new CountryEntity(user.getCountry()));

        if (!groups.isEmpty()) {
            TournamentGroupEntity firstGroup = groups.get(0);
            return Optional.of(firstGroup.toModel());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<TournamentGroup> findByTournament(Tournament tournament) {
        return tournamentGroupRepository.findByTournament(new TournamentEntity()).stream()
                .map(TournamentGroupEntity::toModel)
                .collect(Collectors.toList());
    }

}