package com.macedo.api_recovery_games.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Machine machine;

    @ManyToOne
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal totalConst;

    public Rental() {
    }

    public Long getId() {
        return id;
    }

    public Machine getMachine() {
        return machine;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getTotalConst() {
        return totalConst;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setTotalConst(BigDecimal totalConst) {
        this.totalConst = totalConst;
    }
}
