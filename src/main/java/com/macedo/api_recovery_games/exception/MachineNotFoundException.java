package com.macedo.api_recovery_games.exception;

public class MachineNotFoundException extends RuntimeException{
    public MachineNotFoundException(Long id) {
        super("Maquina com id: "+ id + " não encontrada!");
    }
}
