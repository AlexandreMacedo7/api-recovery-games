package com.macedo.api_recovery_games.exception;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(Long id) {
        super("Locação com id: "+ id + " não encontrada!");
    }
}
