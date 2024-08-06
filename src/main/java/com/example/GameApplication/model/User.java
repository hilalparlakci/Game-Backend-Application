package com.example.GameApplication.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_level")
    private int level;

    @Column(name = "user_coin")
    private int coin;

    @ManyToOne
    @JoinColumn(name = "country_name", nullable = false)
    @JsonBackReference
    private Country country;

    // getter ve setter metodları
}

