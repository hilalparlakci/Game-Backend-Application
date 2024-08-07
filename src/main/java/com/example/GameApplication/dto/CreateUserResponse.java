package com.example.GameApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserResponse {
    private Long id;
    private int level;
    private int coin;
    private String country;


}
