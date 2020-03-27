package com.taurus.javafx.componente;

/**
 *
 * @author Diego
 * @param <T>
 */
public abstract class BotaoCondicionalTableColumnFactory<T> extends BotaoTableColumnFactory<T> {

    public BotaoCondicionalTableColumnFactory(String mensagemTooltip, String estiloBotao) {
        super(mensagemTooltip, estiloBotao);
    }

    @Override
    protected abstract boolean exibirItem(T linhaView);
}
