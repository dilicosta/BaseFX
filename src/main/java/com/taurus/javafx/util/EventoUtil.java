package com.taurus.javafx.util;

import com.taurus.util.GeralUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Diego
 */
public class EventoUtil {

    private static final Log LOG = LogFactory.getLog(EventoUtil.class);

    /**
     * Cria 2 eventos para atualizar automaticamente um ComboBox com o codigo
     * informado em um TextFied e outro para atualizar o TextField quando
     * selecionar um item no ComboBox.
     *
     * @param <B> Tipo do Bean exibido na lista do ComboBox
     * @param <C> Tipo do atributo do codigo
     * @param txtCodigo campo TextField
     * @param comboLista campo ComboBox
     * @param nomeAtributoCodigo nome do atributo codigo
     */
    public static <B, C> void criarEventosCamposCodigoEListaComboBox(TextField txtCodigo, ComboBox<B> comboLista, String nomeAtributoCodigo) {
        criarEventosCamposCodigoEListaComboBoxComMetodo(txtCodigo, comboLista, nomeAtributoCodigo, null, null);
    }

    /**
     * Cria 2 eventos para atualizar automaticamente um ComboBox com o codigo
     * informado em um TextFied e outro para atualizar o TextField quando
     * selecionar um item no ComboBox. Ao executar um dos eventos, executa
     * tambem o metodo informado por parametro da respectiva classe informada.
     *
     * @param <B> Tipo do Bean exibido na lista do ComboBox
     * @param <C> Tipo do atributo do codigo
     * @param txtCodigo campo TextField
     * @param comboLista campo ComboBox
     * @param nomeAtributoCodigo nome do atributo codigo
     * @param objetoComMetodo Objeto instanciado que possui o metodo a ser
     * executado
     * @param nomeMetodo nome do metodo que sera executado
     */
    public static <B, C> void criarEventosCamposCodigoEListaComboBoxComMetodo(TextField txtCodigo, ComboBox<B> comboLista, String nomeAtributoCodigo, Object objetoComMetodo, String nomeMetodo) {
        // Quando selecionar um item no ComboBox
        comboLista.valueProperty().addListener((ObservableValue<? extends B> observable, B oldValue, B newValue) -> {
            if (newValue != null) {
                C codigo = null;
                try {
                    codigo = (C) GeralUtil.getFieldInReflection(newValue, nomeAtributoCodigo);
                } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    LOG.error("Erro ao buscar o valor do campo [" + nomeAtributoCodigo + "], para atualizar o campo código", ex);
                }
                txtCodigo.setText(codigo != null ? codigo.toString() : "");
            } else {
                txtCodigo.setText("");
            }
            executarMetodoPersonalizado(objetoComMetodo, nomeMetodo);
        });

        // Quando digitar o codigo
        txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {

            private String valorEntrada = null;

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // saida de foco
                if (oldValue) {
                    if (valorEntrada != null && !valorEntrada.equals(txtCodigo.getText())) {
                        atualizarComboBox();
                        EventoUtil.executarMetodoPersonalizado(objetoComMetodo, nomeMetodo);
                    }
                } else {
                    valorEntrada = txtCodigo.getText();
                }
            }

            private void atualizarComboBox() {
                boolean encontrou = false;
                Long codigo = null;

                try {
                    codigo = Long.valueOf(txtCodigo.getText());
                } catch (NumberFormatException ex) {
                }

                for (B bean : comboLista.getItems()) {
                    C codigoBean = null;
                    try {
                        codigoBean = (C) GeralUtil.getFieldInReflection(bean, nomeAtributoCodigo);
                    } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        LOG.error("Erro ao buscar o valor do campo [" + nomeAtributoCodigo + "], para atualizar o ComboBox", ex);
                    }
                    if (codigo != null && codigoBean != null && codigoBean.toString().equals(codigo.toString())) {
                        comboLista.setValue(bean);
                        encontrou = true;
                    }
                }
                if (!encontrou) {
                    comboLista.setValue(null);
                }
            }
        });
    }

    private static void executarMetodoPersonalizado(Object objetoComMetodo, String nomeMetodo) {
        if (objetoComMetodo != null) {
            Class objectClass = objetoComMetodo.getClass();
            Method metodo;
            try {
                metodo = objectClass.getMethod(nomeMetodo);
                metodo.invoke(objetoComMetodo);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOG.error("Erro ao executar o metodo personalizado [" + nomeMetodo + "] da classe [" + objetoComMetodo.getClass().getName() + "], no evento de perda de foco.", ex);
            }
        }
    }
}
