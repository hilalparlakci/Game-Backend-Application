package com.example.GameApplication.Impl;

import com.example.GameApplication.dto.UpdateLevelRequest;
import com.example.GameApplication.model.User;
import com.example.GameApplication.model.Country;
import com.example.GameApplication.repo.UserRepository;
import com.example.GameApplication.service.CountryService;
import com.example.GameApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CountryService countryService;

    @Override
    public User createUser() {
        List<Country> countries = countryService.findAll();
        if (countries.isEmpty()) {
            throw new RuntimeException("No countries available");
        }
        Country country = countries.get(new Random().nextInt(countries.size()));

        User user = new User();
        user.setLevel(1);
        user.setCoin(5000);
        user.setCountry(country);

        return userRepository.save(user);
    }

    @Override
    public User updateLevel(UpdateLevelRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        user.setLevel(user.getLevel() + 1);
        user.setCoin(user.getCoin() + 25);

        return userRepository.save(user);
    }
}
