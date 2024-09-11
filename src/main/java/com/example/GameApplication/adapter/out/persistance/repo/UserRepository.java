package com.example.GameApplication.adapter.out.persistance.repo;
import com.example.GameApplication.adapter.out.persistance.repo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
