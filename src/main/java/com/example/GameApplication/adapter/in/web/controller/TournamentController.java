package com.example.GameApplication.adapter.in.web.controller;

import com.example.GameApplication.application.domain.dto.request.EnterTournamentRequest;
import com.example.GameApplication.application.domain.dto.response.EnterTournamentResponse;
import com.example.GameApplication.application.domain.dto.response.ErrorResponse;
import com.example.GameApplication.application.domain.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PostMapping("/enter")
    public ResponseEntity<?> enterTournament(@RequestBody EnterTournamentRequest request) {
        try {
            EnterTournamentResponse response = tournamentService.enterTournament(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}