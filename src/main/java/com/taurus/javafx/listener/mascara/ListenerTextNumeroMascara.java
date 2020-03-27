package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener cria uma
 * mascara para aceitar apenas numeros
 *
 * @author Diego Lima
 */
public class ListenerTextNumeroMascara extends GenericListenerExpressaoRegular<String> {

    public ListenerTextNumeroMascara(StringProperty stringProperty, Integer maximoNumero) {
        super(stringProperty, maximoNumero);
    }

    @Override
    protected String getExpressaoRegular() {
        return "[0-9]*";
    }
}
