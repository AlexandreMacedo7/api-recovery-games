package com.macedo.api_recovery_games.util;

import com.macedo.api_recovery_games.exception.RentalIllegalException;
import com.macedo.api_recovery_games.models.Control;
import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.Rental;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class RentalCalculator {

    public void calculateRentalTime(Machine machine, Integer numberControl, BigDecimal totalCost, Rental rental) {
       //VERIFICA VALOR DO ALUGUEL
        validateInputs(totalCost);

        //VEFICA SE O CONTROLE EXISTE E TRAS O VALOR DELE.
        //TODO CRIAR VALIDAÇÃO ANTERIOR A ENTREDA DE DADOS PARA NUMERO DE CONTROLE PODER REPETIR NA COLUNA, MAS NAO NO OBJETO

        Control control = machine.getControls().stream()
                .filter(c -> c.getNumber().equals(numberControl))
                .findFirst().orElseThrow(() -> new RuntimeException("Não existe esse controle"));

        //CALCULA O TEMPO DO ALUGUEL
        BigDecimal hours = calculateRentalHours(totalCost, BigDecimal.valueOf(control.getValueControl()));
        ensureMinimumRentalTime(hours);

        LocalDateTime startTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime endTime = calculateEndTime(startTime, hours);

        rental.setStartTime(startTime);
        rental.setEndTime(endTime);
        rental.setTotalCost(totalCost);
    }

    private void validateInputs(BigDecimal totalCost) {
        if (totalCost == null) {
            throw new RentalIllegalException("O custo total deve ser um valor positivo!");
        }
    }

    //TODO Observar os valores e tempo, testar os valores que deem 1H, 2H, 45 min, e 30 min e 15min.
    private BigDecimal calculateRentalHours(BigDecimal totalCost, BigDecimal valueControl) {
        return totalCost.divide(valueControl, 2, RoundingMode.HALF_UP);
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
