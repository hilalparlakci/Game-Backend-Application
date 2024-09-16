package com.example.GameApplication.application.domain.service;

import com.example.GameApplication.application.domain.model.Country;
import com.example.GameApplication.application.domain.dto.request.UpdateLevelRequest;
import com.example.GameApplication.application.domain.model.TournamentGroupMember;
import com.example.GameApplication.application.domain.model.User;
import com.example.GameApplication.application.port.out.CountryPersistencePort;
import com.example.GameApplication.application.port.out.UserPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserPersistencePort userPersistencePort;
    private final CountryPersistencePort countryPersistencePort;
    private final TournamentGroupMemberService tournamentGroupMemberService;

    public void validateUserId(Long userId) {
        if (userId == null) {
            throw new RuntimeException("hey no null id entry");
        }
    }
    public User createUser() {
        List<Country> countries = countryPersistencePort.findAll();
        if (countries.isEmpty()) {
            throw new RuntimeException("No countries available");
        }
        Country country = countries.get(new Random().nextInt(countries.size()));

        User user = new User();
        user.setLevel(1);
        user.setCoin(5000);
        user.setCountry(country);

        return userPersistencePort.save(user);
    }

    @Transactional
    public User updateLevel(UpdateLevelRequest request) {
        Long id = request.getUserId();
        validateUserId(id);

        User user = userPersistencePort.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        user.setLevel(user.getLevel() + 1);
        user.setCoin(user.getCoin() + 25);

        return userPersistencePort.save(user);
    }
}
