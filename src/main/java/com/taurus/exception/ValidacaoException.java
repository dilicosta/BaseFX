package com.taurus.exception;

/**
 *
 * @author Diego Lima
 */
public class ValidacaoException extends Exception {

    public ValidacaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidacaoException(String message) {
        super(message);
    }

    public ValidacaoException(Throwable cause) {
        super(cause);
    }
}
