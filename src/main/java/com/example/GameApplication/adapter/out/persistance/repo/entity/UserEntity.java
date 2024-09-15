package com.example.GameApplication.adapter.out.persistance.repo.entity;

import com.example.GameApplication.application.domain.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_level")
    private int level;

    @Column(name = "user_coin")
    private int coin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_name", nullable = false)
    @JsonBackReference
    private CountryEntity country;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TournamentGroupMemberEntity> tournamentGroupMembers = new HashSet<>();


    public User toModel() {
        return new User(
               this.id,
                this.level,
                this.coin,
                this.country.toModel()
        );
    }

    public UserEntity(User model) {
        this.id = model.getId();
        this.level = model.getLevel();
        this.coin = model.getCoin();
        this.country = new CountryEntity(model.getCountry());
    }

}
