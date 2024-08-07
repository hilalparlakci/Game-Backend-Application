package com.example.GameApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateLevelResponse {
    private Long userId;
    private int updatedLevel;
    private int updatedCoin;
    private String country;

}
