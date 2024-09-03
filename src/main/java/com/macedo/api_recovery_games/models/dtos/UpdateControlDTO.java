package com.macedo.api_recovery_games.models.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateControlDTO(@NotNull Long id, @NotNull Double valueControl) {
}
