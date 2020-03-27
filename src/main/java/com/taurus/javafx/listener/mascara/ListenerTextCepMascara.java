package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener cria uma
 * mascara de CEP
 *
 * @author Diego
 */
public class ListenerTextCepMascara extends GenericListenerMascara<String> {

    private final String mascara = "99.999-999";

    public ListenerTextCepMascara(StringProperty stringProperty) {
        super(stringProperty);
    }

    @Override
    protected String getMascara() {
        return mascara;
    }
}
