package com.taurus.exception;

/**
 * Classe que representa as excecoes gerais na camada de persistencia da aplicacao
 *
 * @author Diego Lima
 */
public class PersistenciaException extends Exception {

    public PersistenciaException(String message) {
        super(message);
    }

    public PersistenciaException(Throwable throwable) {
        super(throwable);
    }

    public PersistenciaException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
