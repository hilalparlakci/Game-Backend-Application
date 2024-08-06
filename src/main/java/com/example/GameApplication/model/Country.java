package com.example.GameApplication.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @Column(name = "country_name")
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonManagedReference
    private List<User> users;

    public Country(String name) {
        this.name = name;
    }
}