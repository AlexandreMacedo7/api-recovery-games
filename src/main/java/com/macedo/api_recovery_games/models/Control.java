package com.macedo.api_recovery_games.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "controls")
public class Control {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private Integer number;
    private Double valueControl;
    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    public Control() {
    }
}
