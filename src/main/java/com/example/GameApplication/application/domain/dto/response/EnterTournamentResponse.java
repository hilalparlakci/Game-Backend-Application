package com.example.GameApplication.application.domain.dto.response;

import com.example.GameApplication.application.domain.model.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EnterTournamentResponse {
    private Long tournamentId;
    private List<GroupMember> groupMembers;

}
