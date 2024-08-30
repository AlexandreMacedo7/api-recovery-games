package com.macedo.api_recovery_games.models.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateMachineDTO(@NotBlank String type, List<CreateControlDTO> createControlDTOList) {
}
