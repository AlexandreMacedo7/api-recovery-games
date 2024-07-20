package com.macedo.api_recovery_games.models.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RentalDTO(@NotNull Long machineId, @NotNull Long userId,
                        @NotNull LocalDateTime startTime, @NotNull LocalDateTime endTime,
                        @NotNull BigDecimal totalCost) {
}
