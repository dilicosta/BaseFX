package com.taurus.javafx.listener.mascara;

import com.taurus.util.ExpressaoRegularUtil;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Listener para ser utilizado um TextField JavaFx, com uma mascara para validar
 * os valores de entrada e incluir os caracteres especiais.
 *
 * <b>Mascara:</b>
 * * "9" identifica valores numericos
 *
 * * "a" identifica qualquer letra minuscula
 *
 * * "A" identifica qualquer letra minuscula ou maiuscula
 *
 * "Outros" quaisquer outros caracteres na mascara corresponderao a eles
 * proprios.
 *
 * Exempos:
 *
 * * "99/99/99" corresponde a uma mascara de data. (Nao verifica se eh uma data
 * valida) Casa com "23/12/90" e " 54/22/81"
 *
 * * "a-99" -> casa com "f-23" "g-47"
 *
 * * "g.A" -> casa com "g.b", "g.B", "g.W", "g.g", "g.G"
 *
 * @author Diego
 * @param <T>
 */
public abstract class GenericListenerMascara<T extends String> implements ChangeListener<T> {

    private StringProperty stringProperty;
    private ExpressaoRegularUtil expressaoRegularUtil;

    public GenericListenerMascara(StringProperty stringProperty) {
        this.stringProperty = stringProperty;
        this.expressaoRegularUtil = new ExpressaoRegularUtil(this.getMascara());
    }

    @Override
    public final void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        if (this.stringProperty.getBean() instanceof TextField) {
            TextField textField = (TextField) this.stringProperty.getBean();

            if (!newValue.matches(this.expressaoRegularUtil.getExpressaoRegularValidacaoParcial())) {
                textField.setText(oldValue);
            } else {
                // Verifica se eh preciso colocar o um caracter de mascara
                if (newValue.length() < this.expressaoRegularUtil.getMascara().length()) {
                    char proxCaracter = this.expressaoRegularUtil.getMascara().charAt(newValue.length());
                    if (proxCaracter != '9' && proxCaracter != 'a' && proxCaracter != 'A') {
                        String expressaoComProxCaracter = newValue + String.valueOf(proxCaracter);

                        // se expressaoComProxCaracter == oldValue, esta apagando, nao atualizar
                        if (!expressaoComProxCaracter.equals(oldValue)) {
                            textField.setText(newValue + String.valueOf(proxCaracter));
                        }
                    }
                }
            }
        }
    }

    protected abstract String getMascara();
}
