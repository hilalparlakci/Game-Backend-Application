package com.example.GameApplication.adapter.out.persistance.repo;

import com.example.GameApplication.adapter.out.persistance.repo.entity.UserEntity;
import com.example.GameApplication.application.domain.model.User;
import com.example.GameApplication.application.port.out.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(UserEntity::toModel);
    }

    @Override
    public User save(User user) {
        return userRepository.save(new UserEntity(user)).toModel();
    }

}

