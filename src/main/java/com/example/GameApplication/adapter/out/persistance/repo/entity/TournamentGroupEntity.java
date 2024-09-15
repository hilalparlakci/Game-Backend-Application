package com.example.GameApplication.adapter.out.persistance.repo.entity;

import com.example.GameApplication.application.domain.model.TournamentGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tournamentgroup")
public class TournamentGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_group_id")
    private Long tournamentGroupId;

    @Column(name = "is_started")
    private boolean isStarted;

    @Column(name = "has_ended")
    private boolean hasEnded;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private TournamentEntity tournament;

    @OneToMany(mappedBy = "tournamentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TournamentGroupMemberEntity> tournamentGroupMembers = new HashSet<>();

    public TournamentGroup toModel() {
        return new TournamentGroup(this.tournamentGroupId, this.isStarted, this.hasEnded, this.tournament.toModel());
    }

    public TournamentGroupEntity(TournamentGroup model) {
        this.tournamentGroupId = model.getTournamentGroupId();
        this.isStarted = model.isStarted();
        this.hasEnded = model.isHasEnded();
        this.tournament = new TournamentEntity(model.getTournament());
    }
}