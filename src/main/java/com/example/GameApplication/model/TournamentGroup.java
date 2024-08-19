package com.example.GameApplication.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tournamentgroup")
public class TournamentGroup {

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
    private Tournament tournament;

    @OneToMany(mappedBy = "tournamentGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TournamentGroupMember> tournamentGroupMembers = new HashSet<>();


}