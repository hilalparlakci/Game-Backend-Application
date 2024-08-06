package com.example.GameApplication.service;

import com.example.GameApplication.model.Country;
import java.util.List;

public interface CountryService {
    void addCountryIfNotExists(String name);
    Country findByName(String name);
    List<Country> findAll();
}