package com.example.GameApplication.dto;

import com.example.GameApplication.model.Country;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterTournamentRequest {
    private Long id;
}
