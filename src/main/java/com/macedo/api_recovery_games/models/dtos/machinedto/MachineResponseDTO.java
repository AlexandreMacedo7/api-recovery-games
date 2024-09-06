package com.macedo.api_recovery_games.models.dtos.machinedto;

import com.macedo.api_recovery_games.models.dtos.controldto.ControlResponseDTO;

import java.util.List;

public record MachineResponseDTO(Long id, String type, List<ControlResponseDTO> controlResponseDTOSDTOList) {
}
