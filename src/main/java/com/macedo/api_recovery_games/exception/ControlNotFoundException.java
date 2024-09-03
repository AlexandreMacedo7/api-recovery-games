package com.macedo.api_recovery_games.exception;

public class ControlNotFoundException extends RuntimeException{
    public ControlNotFoundException(Long id) {
        super("Controle com id: "+ id + " não encontrada!");
    }
}
