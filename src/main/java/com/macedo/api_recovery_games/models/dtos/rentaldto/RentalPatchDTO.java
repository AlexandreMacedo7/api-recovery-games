package com.macedo.api_recovery_games.models.dtos.rentaldto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public record RentalPatchDTO(Optional<LocalDateTime> startTime,
                             Optional<LocalDateTime> endTime,
                             Optional<BigDecimal> totalCost) {

    public RentalPatchDTO {
        startTime = startTime != null ? startTime : Optional.empty();
        endTime = endTime != null ? endTime : Optional.empty();
        totalCost = totalCost != null ? totalCost : Optional.empty();
    }
}
