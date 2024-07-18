package com.macedo.api_recovery_games.repository;

import com.macedo.api_recovery_games.models.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
}
