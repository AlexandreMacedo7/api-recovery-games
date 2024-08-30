package com.macedo.api_recovery_games.models.dtos;

import java.util.List;

public record MachineResponseDTO(Long id, String type, List<ControlResponseDTO> controlResponseDTOSDTOList) {
}
