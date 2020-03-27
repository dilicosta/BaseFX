package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener limita o
 * tamanho de caracteres no campo.
 *
 * @author Diego Lima
 */
public class ListenerTextMaximoCaracteres extends GenericListenerExpressaoRegular {

    public ListenerTextMaximoCaracteres(StringProperty stringProperty, Integer maximoNumero) {
        super(stringProperty, maximoNumero);
    }

    @Override
    protected String getExpressaoRegular() {
        return ".*";
    }

}
