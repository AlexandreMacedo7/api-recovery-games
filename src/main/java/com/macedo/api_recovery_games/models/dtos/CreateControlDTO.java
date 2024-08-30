package com.macedo.api_recovery_games.models.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateControlDTO(@NotNull Integer number, @NotNull Double valueControl) {
}
