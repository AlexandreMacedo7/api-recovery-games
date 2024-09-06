package com.macedo.api_recovery_games.models.dtos.machinedto;

import jakarta.validation.constraints.NotBlank;

public record MachinePatchDTO(@NotBlank String type) {
}
