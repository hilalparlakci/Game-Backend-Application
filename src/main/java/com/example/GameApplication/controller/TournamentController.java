package com.example.GameApplication.controller;

import com.example.GameApplication.dto.EnterTournamentRequest;
import com.example.GameApplication.dto.EnterTournamentResponse;
import com.example.GameApplication.dto.ErrorResponse;
import com.example.GameApplication.service.TournamentService;
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