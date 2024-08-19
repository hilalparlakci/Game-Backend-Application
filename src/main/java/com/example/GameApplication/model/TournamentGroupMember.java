package com.example.GameApplication.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@Table(name = "tournamentgroupmember")
public class TournamentGroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_group_member_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tournament_group_id")
    private Long tournamentGroupId;

    @Column(name = "score")
    private int score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id", referencedColumnName = "reward_id")
    private Reward reward;

    @Getter
    @ManyToOne
    @JoinColumn(name = "tournament_group_id", insertable = false, updatable = false)
    private TournamentGroup tournamentGroup;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public TournamentGroupMember(Long userId, Long tournamentGroupId) {
        this.userId = userId;
        this.tournamentGroupId = tournamentGroupId;
        this.score = 0; // Default score
    }

}
