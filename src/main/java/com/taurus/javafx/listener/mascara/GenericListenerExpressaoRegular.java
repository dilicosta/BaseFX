package com.taurus.javafx.listener.mascara;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Listener para ser utilizado um TextField JavaFx. Este listener valida somente
 * valores do TextField que estiverem de acordo com a ER informada.
 *
 * @author Diego Lima
 * @param <T>
 */
public abstract class GenericListenerExpressaoRegular<T extends String> implements ChangeListener<T> {

    private StringProperty stringProperty;
    private Integer maximoNumero;

    public GenericListenerExpressaoRegular(StringProperty stringProperty, Integer maximoNumero) {
        this.stringProperty = stringProperty;
        this.maximoNumero = maximoNumero;
    }

    @Override
    public final void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        if (stringProperty.getBean() instanceof TextField) {
            TextField textField = (TextField) stringProperty.getBean();

            if (textField.getText() == null) {
                return;
            }

            if (!newValue.matches(getExpressaoRegular())) {
                textField.setText(oldValue);
            } else {
                if (maximoNumero != null && newValue.length() > maximoNumero) {
                    textField.setText(oldValue);
                }
            }
        }
    }

    protected abstract String getExpressaoRegular();
}
