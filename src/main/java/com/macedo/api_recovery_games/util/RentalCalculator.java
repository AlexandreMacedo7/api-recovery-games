package com.macedo.api_recovery_games.util;

import com.macedo.api_recovery_games.exception.RentalIllegalException;
import com.macedo.api_recovery_games.models.Rental;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class RentalCalculator {

    public void calculateRentalTime(Rental rental, BigDecimal machineHourlyRate, BigDecimal totalCost) {
        validateInputs(machineHourlyRate, totalCost);

        BigDecimal hours = calculateRentalHours(totalCost, machineHourlyRate);
        ensureMinimumRentalTime(hours);

        LocalDateTime startTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime endTime = calculateEndTime(startTime, hours);

        rental.setStartTime(startTime);
        rental.setEndTime(endTime);
        rental.setTotalCost(totalCost);
    }

    private void validateInputs(BigDecimal hourlyRate, BigDecimal totalCost) {
        if (totalCost == null) {
            throw new RentalIllegalException("O custo total deve ser um valor positivo!");
        }
    }

    private BigDecimal calculateRentalHours(BigDecimal totalCost, BigDecimal hourlyRate) {
        return totalCost.divide(hourlyRate, 2, RoundingMode.HALF_UP);
    }

    private void ensureMinimumRentalTime(BigDecimal hours) {
        BigDecimal limitTime = BigDecimal.valueOf(30).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        if (hours.compareTo(limitTime) < 0) {
            throw new RentalIllegalException("Aluguel mínimo de 30 minutos.");
        }
    }

    private LocalDateTime calculateEndTime(LocalDateTime startTime, BigDecimal hours) {
        long hoursPart = hours.longValue();
        long minutesPart = hours.subtract(BigDecimal.valueOf(hoursPart)).multiply(BigDecimal.valueOf(60)).longValue();
        return startTime.plusHours(hoursPart).plusMinutes(minutesPart);
    }
}
