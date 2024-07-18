package com.macedo.api_recovery_games.repository;

import com.macedo.api_recovery_games.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
