package com.example.GameApplication.repo;

import com.example.GameApplication.model.Tournament;
import com.example.GameApplication.model.TournamentGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentGroupRepository extends JpaRepository<TournamentGroup, Long> {
    List<TournamentGroup> findByTournament(Tournament tournament);
}