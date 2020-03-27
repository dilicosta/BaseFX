package com.taurus.javafx.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 * Cria um listener para TextField que executa um metodo na saida de foco quando
 * o valor do TextField eh alterado
 *
 * @author Diego
 */
public abstract class SaidaFocoListener implements ChangeListener<Boolean> {

    private String valorEntrada = null;
    private TextField textField;

    public SaidaFocoListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (textField != null) {
            String valorAtual = textField.getText();
            if (newValue) {
                valorEntrada = valorAtual;
            } else if (valorAtual != null && !valorAtual.equals(valorEntrada)) {
                this.executar();
            }
        }
    }

    public abstract void executar();
}
