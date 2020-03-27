package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener cria uma
 * mascara de CPF
 *
 * @author Diego
 */
public class ListenerTextCpfMascara extends GenericListenerMascara<String> {

    private final String mascara = "999.999.999-99";
    //private final String mascaraCpf = "[0-9]{1,3}|[0-9]{1,3}\\.|[0-9]{1,3}\\.[0-9]{1,3}|[0-9]{1,3}\\.[0-9]{1,3}\\.|[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}|[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}-|[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}-[0-9]{1,2}";

    public ListenerTextCpfMascara(StringProperty stringProperty) {
        super(stringProperty);
    }

    @Override
    protected String getMascara() {
        return mascara;
    }
}
