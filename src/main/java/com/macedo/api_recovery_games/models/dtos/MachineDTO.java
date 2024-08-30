package com.macedo.api_recovery_games.models.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MachineDTO(@NotBlank String type, List<ControlDTO> controlDTOList) {
}
