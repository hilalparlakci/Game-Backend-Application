package com.example.GameApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnterTournamentResponse {
    private Long tournamentId;
    private List<TournamentGroupMemberDTO> groupMembers;

}
