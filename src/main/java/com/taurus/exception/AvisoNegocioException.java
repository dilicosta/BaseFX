package com.taurus.exception;

/**
 * Classe que representa a excecao gerada ao tentar excluir um registro que
 * possui relacionamentos com outros
 *
 * @author Diego Lima
 */
public class AvisoNegocioException extends Exception {

    public AvisoNegocioException(String message) {
        super(message);
    }

    public AvisoNegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
