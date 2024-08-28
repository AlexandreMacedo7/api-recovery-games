package com.macedo.api_recovery_games.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Rental> rentals = new ArrayList<>();

    public User() {
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
        rental.setUser(this);
    }
}
