package com.macedo.api_recovery_games.models.dtos;

import jakarta.validation.constraints.NotNull;

public record ControlDTO(@NotNull Integer number, @NotNull Double valueControl) {
}
