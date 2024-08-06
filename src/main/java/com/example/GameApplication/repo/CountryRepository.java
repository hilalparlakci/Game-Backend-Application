package com.example.GameApplication.repo;

import com.example.GameApplication.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
