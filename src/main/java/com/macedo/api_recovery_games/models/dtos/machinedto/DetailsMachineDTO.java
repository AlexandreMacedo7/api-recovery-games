package com.macedo.api_recovery_games.models.dtos.machinedto;

import com.macedo.api_recovery_games.models.dtos.controldto.DetailsControlDTO;

import java.util.List;

public record DetailsMachineDTO(Long id, String type, List<DetailsControlDTO> detailsControlDTOS) {
}
