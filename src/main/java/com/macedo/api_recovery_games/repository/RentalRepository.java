package com.macedo.api_recovery_games.repository;

import com.macedo.api_recovery_games.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
