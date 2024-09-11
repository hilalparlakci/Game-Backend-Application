package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryPersistencePort {
    List<Country> findAll();

    Optional<Country> findByName(String name);

    void save(Country country);
}