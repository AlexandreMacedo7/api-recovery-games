package com.macedo.api_recovery_games.exception;

public class MachineNotAvailableException extends RuntimeException{
    public MachineNotAvailableException(Long id) {
        super("Maquina com id: "+ id + ", não está disponível para locação!");
    }
}
