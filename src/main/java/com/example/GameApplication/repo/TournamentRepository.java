package com.example.GameApplication.repo;

import com.example.GameApplication.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByDate(LocalDate date);
}
