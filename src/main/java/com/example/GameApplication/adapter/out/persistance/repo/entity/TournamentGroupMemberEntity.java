package com.example.GameApplication.adapter.out.persistance.repo.entity;

import com.example.GameApplication.application.domain.model.TournamentGroupMember;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tournamentgroupmember")
public class TournamentGroupMemberEntity {

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
    private RewardEntity reward;

    @Getter
    @ManyToOne
    @JoinColumn(name = "tournament_group_id", insertable = false, updatable = false)
    private TournamentGroupEntity tournamentGroup;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    public TournamentGroupMember toModel() {
        return new TournamentGroupMember(
                this.getId(),
                this.getUserId(),
                this.getTournamentGroupId(),
                this.getScore()
        );
    }
    
    public TournamentGroupMemberEntity(TournamentGroupMember model) {
        this.setId(model.getId());
        this.setUserId(model.getUserId());
        this.setTournamentGroupId(model.getTournamentGroupId());
        this.setScore(model.getScore());
    }
}
