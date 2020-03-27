package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener cria uma
 * mascara para aceitar valores numericos com ate 2 casas decimais
 *
 * @author Diego Lima
 */
public class ListenerTextMoedaMascara extends GenericListenerExpressaoRegular<String> {

    private final String mascara = "[0-9]*|[0-9]+,[0-9]{0,2}";

    public ListenerTextMoedaMascara(StringProperty stringProperty, Integer maximoNumero) {
        super(stringProperty, maximoNumero);
    }

    @Override
    protected String getExpressaoRegular() {
        return mascara;
    }
}
