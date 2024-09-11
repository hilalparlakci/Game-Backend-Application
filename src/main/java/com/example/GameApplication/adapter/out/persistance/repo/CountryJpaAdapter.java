package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.CountryEntity;
import com.example.GameApplication.application.domain.model.Country;
import com.example.GameApplication.application.port.out.CountryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryJpaAdapter implements CountryPersistencePort{

    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll()
                .stream()
                .map(CountryEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Country> findByName(String name) {
        return countryRepository.findByName(name)
                .map(CountryEntity::toModel);
    }

    @Override
    public void save(Country country){
        countryRepository.save(new CountryEntity(country));
    }

}
