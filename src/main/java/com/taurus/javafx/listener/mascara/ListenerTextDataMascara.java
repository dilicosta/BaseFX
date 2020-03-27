package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener cria uma
 * mascara de data.
 *
 * @author Diego
 */
public class ListenerTextDataMascara extends GenericListenerMascara {

    private final String mascara = "99/99/9999";

    public ListenerTextDataMascara(StringProperty stringProperty) {
        super(stringProperty);
    }

    @Override
    protected String getMascara() {
        return mascara;
    }
}
