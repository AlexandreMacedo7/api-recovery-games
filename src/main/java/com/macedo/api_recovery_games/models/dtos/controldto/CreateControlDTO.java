package com.macedo.api_recovery_games.models.dtos.controldto;

import jakarta.validation.constraints.NotNull;

public record CreateControlDTO(@NotNull Integer number, @NotNull Double valueControl) {
}
