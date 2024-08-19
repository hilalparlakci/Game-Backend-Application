package com.example.GameApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TournamentGroupMemberDTO {
    private Long userId;
    private int score;
    private String country;

}
