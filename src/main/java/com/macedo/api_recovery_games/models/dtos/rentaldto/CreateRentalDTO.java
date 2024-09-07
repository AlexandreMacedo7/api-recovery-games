package com.macedo.api_recovery_games.models.dtos.rentaldto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRentalDTO(@NotNull Long machineId, @NotNull Integer numberControl, @NotNull Long userId,
                              @NotNull BigDecimal totalCost) {
}
