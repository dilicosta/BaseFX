package com.taurus.exception;

/**
 * Classe que representa as excecoes gerais na camada de negocio da aplicacao
 *
 * @author Diego Lima
 */
public class NegocioException extends Exception {

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(Throwable throwable) {
        super(throwable);
    }

    public NegocioException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
