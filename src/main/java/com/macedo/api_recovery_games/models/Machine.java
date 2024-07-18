package com.macedo.api_recovery_games.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String type;
    private BigDecimal hourlyRate;

    @OneToMany(mappedBy = "machine")
    private List<Rental> rentals;

    public Machine() {
    }

    public Long getId() {
        return Id;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
