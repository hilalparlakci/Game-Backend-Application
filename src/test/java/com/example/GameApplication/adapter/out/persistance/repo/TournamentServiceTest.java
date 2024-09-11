package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.application.domain.dto.request.EnterTournamentRequest;
import com.example.GameApplication.application.domain.dto.response.EnterTournamentResponse;
import com.example.GameApplication.application.domain.model.Country;
import com.example.GameApplication.application.domain.model.Tournament;
import com.example.GameApplication.application.domain.model.TournamentGroup;
import com.example.GameApplication.application.domain.model.User;
import com.example.GameApplication.application.domain.service.TournamentService;
import com.example.GameApplication.application.port.out.TournamentGroupMemberPersistencePort;
import com.example.GameApplication.application.port.out.TournamentPersistencePort;
import com.example.GameApplication.application.port.out.TournamentGroupPersistencePort;
import com.example.GameApplication.application.port.out.UserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTest {

    @InjectMocks
    private TournamentService tournamentService;

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private TournamentPersistencePort tournamentPersistencePort;

    @Mock
    private TournamentGroupPersistencePort tournamentGroupPersistencePort;

    @Mock
    private TournamentGroupMemberPersistencePort tournamentGroupMemberPersistencePort;

    @Test
    public void testEnterTournament_Success() {
        Long userId = 5L;
        EnterTournamentRequest request = new EnterTournamentRequest();
        request.setId(userId);

        Country country = new Country("Türkiye");
        User user = new User(userId, 20, 2500, country);

        Tournament tournament = new Tournament(1L, LocalDate.of(2024, 9, 9));
        TournamentGroup group = new TournamentGroup();
        group.setTournamentGroupId(1L);
        group.setTournament(tournament);

        when(userPersistencePort.findById(userId)).thenReturn(Optional.of(user));
        when(tournamentPersistencePort.findByDate(LocalDate.of(2024, 9, 9))).thenReturn(Optional.of(tournament));
        when(tournamentGroupPersistencePort.findById(group.getTournamentGroupId())).thenReturn(Optional.of(group));
        when(tournamentGroupPersistencePort.getSuitableTournamentGroup(tournament, user)).thenReturn(Optional.of(group));
        when(tournamentGroupMemberPersistencePort.findByTournamentGroupId(group.getTournamentGroupId()))
                .thenReturn(Collections.emptyList());

        EnterTournamentResponse response = tournamentService.enterTournament(request);

        assertNotNull(response);
        assertEquals(response.getTournamentId(), tournament.getTournamentId());
        assertTrue(response.getGroupMembers().isEmpty());
    }
}