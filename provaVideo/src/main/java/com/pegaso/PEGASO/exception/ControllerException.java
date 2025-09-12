package com.pegaso.PEGASO.exception;


import lombok.Getter;

@Getter
// Classe per rappresentare un'eccezione del controller
public class ControllerException extends Exception {
    private final int errorCode;

    /**
     * Costruttore principale con messaggio ed errore.
     * @param message Messaggio descrittivo dell'errore.
     * @param errorCode Codice di errore (es. 404, 500).
     */
    public ControllerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    /**
     * Restituisce una rappresentazione leggibile dell'eccezione.
     * @return Stringa formattata contenente il codice e il messaggio.
     */
    @Override
    public String toString() {
        return String.format("[ControllerException %d]: %s", errorCode, getMessage());
    }
}

