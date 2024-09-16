package com.example.GameApplication.application.domain.service;

import com.example.GameApplication.application.domain.model.TournamentGroupMember;
import com.example.GameApplication.application.port.out.TournamentGroupMemberPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentGroupMemberService {
    private final TournamentGroupMemberPersistencePort tournamentGroupMemberPersistencePort;
    public void incrementScore(Long userId) {
        Optional<TournamentGroupMember> tournamentGroupMember = tournamentGroupMemberPersistencePort.findByUserId(userId);

        if(tournamentGroupMember.isPresent()) {
            tournamentGroupMember.get().setScore(tournamentGroupMember.get().getScore()+1);
            tournamentGroupMemberPersistencePort.save(tournamentGroupMember.get());
        }
    }
}
