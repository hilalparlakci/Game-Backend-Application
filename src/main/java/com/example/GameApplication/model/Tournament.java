package com.example.GameApplication.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "tournament_date")
    private LocalDate date;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TournamentGroup> tournamentGroups = new HashSet<>();

    public Tournament(LocalDate date) {
        this.date = date;
    }

}