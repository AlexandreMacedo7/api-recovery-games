package com.macedo.api_recovery_games.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private boolean available = true;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rental> rentals = new ArrayList<>();

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Control> controls = new ArrayList<>();

    public Machine() {
    }

    public void addControl(Control control) {
        controls.add(control);
        control.setMachine(this);
    }

    public void removeControl(Control control) {
        controls.remove(control);
        control.setMachine(null);
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setMachine(this);
    }

    public void removeRental(Rental rental) {
        rentals.remove(rental);
        rental.setMachine(null);
    }
}
