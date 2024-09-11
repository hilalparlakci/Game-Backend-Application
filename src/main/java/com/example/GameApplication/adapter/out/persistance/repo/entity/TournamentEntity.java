package com.example.GameApplication.adapter.out.persistance.repo.entity;

import com.example.GameApplication.application.domain.model.Tournament;
import com.example.GameApplication.application.domain.model.TournamentGroup;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tournament")
public class TournamentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "tournament_date")
    private LocalDate date;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TournamentGroupEntity> tournamentGroups = new HashSet<>();

    public TournamentEntity(LocalDate date) { this.date = date; }

    public Tournament toModel() {

        return new Tournament( this.getTournamentId(), this.getDate() );
    }

    public TournamentEntity(Tournament model) {
        this.tournamentId = model.getTournamentId();
        this.date = model.getDate();
    }
}