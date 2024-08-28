package com.macedo.api_recovery_games.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "machines")
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String type;
    private BigDecimal hourlyRate;
    private boolean available = true;

    @OneToMany(mappedBy = "machine")
    private List<Rental> rentals = new ArrayList<>();

    public Machine() {
    }

    public void AddRental(Rental rental) {
        rentals.add(rental);
        rental.setMachine(this);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
        rental.setMachine(null);
    }
}
