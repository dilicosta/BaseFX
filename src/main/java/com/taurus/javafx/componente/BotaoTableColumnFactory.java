package com.taurus.javafx.componente;

/**
 *
 * @author Diego
 * @param <T> Objeto que representa a linha da tabela
 */
public abstract class BotaoTableColumnFactory<T> extends BotaoVariavelTableColumnFactory<T> {

    private String mensagemTooltip;
    private String estiloBotao;

    public BotaoTableColumnFactory(String mensagemTooltip, String estiloBotao) {
        this.mensagemTooltip = mensagemTooltip;
        this.estiloBotao = estiloBotao;
    }

    @Override
    protected boolean exibirItem(T linhaView) {
        return true;
    }

    @Override
    public final DadosBotao getDadosBotao(T linhaView) {
        return new DadosBotao(estiloBotao, mensagemTooltip);
    }
}
