package com.example.GameApplication.Impl;
import com.example.GameApplication.repo.CountryRepository;
import com.example.GameApplication.model.Country;
import com.example.GameApplication.service.CountryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @PostConstruct
    public void init() {
        addCountryIfNotExists("Türkiye");
        addCountryIfNotExists("United States");
        addCountryIfNotExists("United Kingdom");
        addCountryIfNotExists("France");
        addCountryIfNotExists("Germany");
    }

    @Override
    public void addCountryIfNotExists(String name) {
        if (countryRepository.findByName(name) == null) {
            Country country = new Country(name);
            countryRepository.save(country);
        }
    }

    @Override
    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}