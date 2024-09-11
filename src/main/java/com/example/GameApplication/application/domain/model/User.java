package com.example.GameApplication.application.domain.model;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;
    private int level;
    private int coin;
    private Country country;

}

