package com.taurus.javafx.listener;

import com.taurus.util.ListaConstantesBase;
import com.taurus.util.FormatarUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;

/**
 *
 * @author Diego
 */
public class CampoPositivoNegativo implements ChangeListener<String> {

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (observable instanceof SimpleStringProperty && ((SimpleStringProperty) observable).getBean() instanceof Node) {
            Node node = (Node) ((SimpleStringProperty) observable).getBean();
            Float valor = FormatarUtil.getNumero(newValue);
            if (valor != null) {
                node.getStyleClass().clear();
                if (valor < 0) {
                    node.getStyleClass().add(ListaConstantesBase.EstiloCss.TEXTO_VERMELHO.getValor());
                } else if (valor > 0) {
                    node.getStyleClass().add(ListaConstantesBase.EstiloCss.TEXTO_AZUL.getValor());
                }
            }
        }
    }
}
