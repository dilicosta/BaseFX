package com.taurus.javafx.listener.mascara;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;

/**
 * Listener para ser utilizado um DatePicker JavaFx para definir a mascara de
 * data.
 * 
 * Utilizar ListerTextDataMascara 
 *
 * @author Diego
 */
@Deprecated
public class ListenerDatePickerMascara<T> implements ChangeListener<T> {

    private final DatePicker data;

    public ListenerDatePickerMascara(DatePicker datePicker) {
        this.data = datePicker;
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {

        String digitos = data.getEditor().getText().replaceAll("[^0-9]", "");
        if (digitos.length() > 8) {
            digitos = digitos.substring(0, 8);
        }
        StringBuilder resultado = new StringBuilder();

        int i = 1;
        for (char c : digitos.toCharArray()) {
            if (i == 3 || i == 5) {
                resultado.append("/");
            }
            resultado.append(c);
            i++;
        }

        if (!data.getEditor().getText().equals(resultado.toString())) {
            data.getEditor().setText(resultado.toString());
        }
    }
}
