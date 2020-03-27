package com.taurus.dao;

/**
 *
 * @author Diego
 */
public class ParametroPesquisa {

    private String nomeAtributo;
    private Object valorAtributo;
    private Operador operador;

    /**
     * Operador usados na comparacao de atributos
     */
    public enum Operador {
        IGUAL, DIFERENTE, MAIOR, MAIOR_IGUAL, MENOR, MENOR_IGUAL, LIKE;
    };

    public ParametroPesquisa(String nomeAtributo, Object valorAtributo, Operador operador) {
        this.nomeAtributo = nomeAtributo;
        this.valorAtributo = valorAtributo;
        this.operador = operador;
    }

    public String getNomeAtributo() {
        return nomeAtributo;
    }

    public void setNomeAtributo(String nomeAtributo) {
        this.nomeAtributo = nomeAtributo;
    }

    public Object getValorAtributo() {
        return valorAtributo;
    }

    public void setValorAtributo(Object valorAtributo) {
        this.valorAtributo = valorAtributo;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }
}
