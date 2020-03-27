package com.taurus.javafx.listener.mascara;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.TextField;

/**
 * Listener para ser utilizado um TextField JavaFx, com uma mascara para numeros
 * Os numeros devem ser representados por #. Ex #,##
 *
 * Utilizar GenericListenerMascara.
 *
 * @author Diego
 */
@Deprecated
public abstract class GenericListenerNumeroMascara implements InvalidationListener {

    private final TextField txtNumero;
    private final String mascara;

    public GenericListenerNumeroMascara(TextField textField, String mascara) {
        this.txtNumero = textField;
        this.mascara = mascara;
    }

    @Override
    public void invalidated(Observable observable) {
        if (txtNumero.getText() == null) {
            return;
        }
        String digitos = this.getDigitos();
        StringBuilder resultado = new StringBuilder();
        int i = 0;
        int quant = 0;

        if (txtNumero.getText().length() <= mascara.length()) {
            while (i < mascara.length()) {
                if (quant < digitos.length()) {
                    if ("#".equals(mascara.substring(i, i + 1))) {
                        String caracter = digitos.substring(quant, quant + 1);
                        if (caracter.matches("[0-9]")) {
                            resultado.append(caracter);
                        }
                        quant++;
                    } else {
                        resultado.append(mascara.substring(i, i + 1));
                    }
                }
                i++;
            }
            if (!txtNumero.getText().equals(resultado.toString())) {
                txtNumero.setText(resultado.toString());
            }
        }
        if (txtNumero.getText().length() > mascara.length()) {
            txtNumero.setText(txtNumero.getText(0, mascara.length()));
        }
    }

    private String getDigitos() {
        String separadores = this.mascara.replaceAll("\\#", "");
        StringBuilder mascaraSeparadores = new StringBuilder("[");
        for (char c : separadores.toCharArray()) {
            if (!mascaraSeparadores.toString().contains("" + c)) {
                mascaraSeparadores.append(c);
            }
        }
        mascaraSeparadores.append("]");
        if (mascaraSeparadores.length() > 2) {
            return this.txtNumero.getText().replaceAll(mascaraSeparadores.toString(), "");
        } else {
            return this.txtNumero.getText();
        }
    }
}
