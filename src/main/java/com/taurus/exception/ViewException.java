package com.taurus.exception;

/**
 * Classe que representa as excecoes gerais na camada View
 *
 * @author Diego Lima
 */
public class ViewException extends Exception {

    public ViewException(String message) {
        super(message);
    }

    public ViewException(Throwable throwable) {
        super(throwable);
    }

    public ViewException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
