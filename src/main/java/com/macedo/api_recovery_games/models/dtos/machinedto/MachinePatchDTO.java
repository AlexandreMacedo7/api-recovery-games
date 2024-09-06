package com.macedo.api_recovery_games.models.dtos.machinedto;

import java.math.BigDecimal;
import java.util.Optional;

public record MachinePatchDTO(Optional<String> type, Optional<BigDecimal> hourlyRate) {

    public MachinePatchDTO {
        type = type != null ? type : Optional.empty();
    }
}
