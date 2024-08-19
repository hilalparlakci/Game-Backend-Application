package com.example.GameApplication.service;

import com.example.GameApplication.dto.EnterTournamentRequest;
import com.example.GameApplication.dto.EnterTournamentResponse;
import com.example.GameApplication.model.Tournament;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

public interface TournamentService {
    @Scheduled(cron = "0 0 0 * * ?", zone = "UTC")
    void startNewTournament();

    Tournament getActiveTournament();

    boolean isUserInActiveTournament(Long userId);

    @Transactional
    void updateTournamentGroupStatus(Long tournamentGroupId);

    @Transactional
    EnterTournamentResponse enterTournament(EnterTournamentRequest request);
}
