package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupEntity;
import com.example.GameApplication.adapter.out.persistance.repo.entity.TournamentGroupMemberEntity;
import com.example.GameApplication.application.domain.model.TournamentGroup;
import com.example.GameApplication.application.domain.model.TournamentGroupMember;
import com.example.GameApplication.application.port.out.TournamentGroupMemberPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TournamentGroupMemberJpaAdapter implements TournamentGroupMemberPersistencePort {

    private final TournamentGroupMemberRepository tournamentGroupMemberRepository;

    @Override
    public boolean existsByUserIdAndTournamentGroup_Tournament_Date(Long userId, LocalDate date) {
        return tournamentGroupMemberRepository.existsByUserIdAndTournamentGroup_Tournament_Date(userId, date);
    }

    @Override
    public long countMembersByTournamentGroup(TournamentGroup group) {
        return tournamentGroupMemberRepository.countMembersByTournamentGroup(new TournamentGroupEntity(group));
    }

    @Override
    public TournamentGroupMember save(TournamentGroupMember groupMember) {
        return tournamentGroupMemberRepository.save(new TournamentGroupMemberEntity(groupMember)).toModel();
    }

    @Override
    public List<TournamentGroupMember> findByTournamentGroupId(Long tournamentGroupId) {
        return tournamentGroupMemberRepository.findByTournamentGroupId(tournamentGroupId).stream()
                .map(TournamentGroupMemberEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TournamentGroupMember> findById(Long tournamentGroupMemberId) {
        return tournamentGroupMemberRepository.findById(tournamentGroupMemberId)
                .map(TournamentGroupMemberEntity::toModel);
    }

}