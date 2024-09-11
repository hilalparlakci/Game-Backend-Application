package com.example.GameApplication.application.domain.service;

import com.example.GameApplication.application.domain.model.Country;
import com.example.GameApplication.application.port.out.CountryPersistencePort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryPersistencePort countryPersistencePort;

    @PostConstruct
    public void init() {
        addCountryIfNotExists("Türkiye");
        addCountryIfNotExists("United States");
        addCountryIfNotExists("United Kingdom");
        addCountryIfNotExists("France");
        addCountryIfNotExists("Germany");
    }

    public void addCountryIfNotExists(String name) {
        Optional<Country> country = countryPersistencePort.findByName(name);
        country.ifPresentOrElse(
                c -> {},
                () -> {
                    Country newCountry = new Country(name);
                    countryPersistencePort.save(newCountry);
                }
        );
    }
    public Optional<Country> findByName(String name) {
        return countryPersistencePort.findByName(name);
    }

    public List<Country> findAll() {
        return countryPersistencePort.findAll();
    }
}