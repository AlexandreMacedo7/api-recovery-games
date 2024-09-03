package com.macedo.api_recovery_games.models.dtos;

import java.util.List;

public record DetailsMachineDTO(Long id, String type, List<DetailsControlDTO> detailsControlDTOS) {
}
