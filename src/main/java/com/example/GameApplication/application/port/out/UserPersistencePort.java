package com.example.GameApplication.application.port.out;

import com.example.GameApplication.application.domain.model.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findById(Long id);
    User save(User user);
}
