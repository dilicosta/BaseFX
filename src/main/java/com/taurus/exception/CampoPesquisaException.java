package com.taurus.exception;

/**
 * Classe que representa uma excecao especifica quando se realiza uma pesquisa
 * atraves dos atributos de uma entidade, e esta entidade possui valor null para
 * todos os seus atributos que representam um campo na sua respectiva tabela
 * mapeada
 *
 * @author Diego - TJMG
 */
public class CampoPesquisaException extends Exception {

    public CampoPesquisaException(String message) {
        super(message);
    }

    public CampoPesquisaException(Throwable throwable) {
        super(throwable);
    }

    public CampoPesquisaException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
