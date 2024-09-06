package com.macedo.api_recovery_games.models.dtos.userdto;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(@NotBlank String name, @NotBlank String phone) {
}
