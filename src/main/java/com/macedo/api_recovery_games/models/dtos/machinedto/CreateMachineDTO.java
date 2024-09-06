package com.macedo.api_recovery_games.models.dtos.machinedto;

import com.macedo.api_recovery_games.models.dtos.controldto.CreateControlDTO;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateMachineDTO(@NotBlank String type, List<CreateControlDTO> createControlDTOList) {
}
