package com.taurus.javafx.util;

import com.taurus.javafx.controller.FXMLBaseController;
import com.taurus.util.ListaConstantesBase;
import com.taurus.util.GeralUtil;
import com.taurus.javafx.listener.mascara.ListenerTextCpfMascara;
import com.taurus.javafx.listener.mascara.ListenerTextDataMascara;
import com.taurus.javafx.listener.mascara.ListenerTextMaximoCaracteres;
import com.taurus.javafx.listener.mascara.ListenerTextNumeroMascara;
import com.taurus.util.annotation.Mascara;
import com.taurus.util.annotation.ValidarCampo;
import com.taurus.javafx.componente.TooltipRapido;
import com.taurus.javafx.listener.mascara.GenericListenerMascara;
import com.taurus.javafx.listener.mascara.ListenerTextCepMascara;
import com.taurus.javafx.listener.mascara.ListenerTextMoedaMascara;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.Tooltip;
import org.apache.commons.validator.GenericValidator;
import org.fxutils.maskedtextfield.MaskedTextField;

/**
 *
 * @author Diego
 */
public class ValidarCampoUtil {

    private static final Tooltip TOOL_TIP = new TooltipRapido("Este campo é obrigatório.");

    /**
     * Verifica se os campos foram preenchidos e marca aqueles que sao
     * obrigatorios
     *
     * @param itemToCheck lista de itens a verificar
     * @return true se todos campos foram preenchidos, false se um campo não for
     * preenchido
     */
    public static boolean validarCampos(Node... itemToCheck) {
        //used to determinate how many fields failed in validation
        List<Node> failedFields = new ArrayList<>();
        TOOL_TIP.getStyleClass().add(ListaConstantesBase.EstiloCss.TOOLTIP_VALIDACAO.getValor());

        for (Node n : itemToCheck) {
            boolean valido = true;
            // Validate MaskedTextField
            if (n instanceof MaskedTextField) {
                MaskedTextField maskedTextField = (MaskedTextField) n;
                maskedTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    removeToolTipAndBorderColor(maskedTextField, TOOL_TIP);
                });
                if (maskedTextField.getPlainText() == null || maskedTextField.getPlainText().trim().equals("")) {
                    valido = false;
                }
            } // Validate TextFields
            else if (n instanceof TextField || n instanceof DatePicker) {
                TextField textField;
                if (n instanceof DatePicker) {
                    textField = ((DatePicker) n).getEditor();
                } else {
                    textField = (TextField) n;
                }
                textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    removeToolTipAndBorderColor(n, TOOL_TIP);
                });
                if (textField.getText() == null || textField.getText().trim().equals("")) {
                    valido = false;
                }
            } // Validate Combo Box
            else if (n instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) n;
                comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
                    removeToolTipAndBorderColor(n, TOOL_TIP);
                });
                if (comboBox.getValue() == null) {
                    valido = false;
                }
            } // Validate TextArea
            else if (n instanceof TextArea) {
                TextArea textArea = (TextArea) n;
                textArea.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    removeToolTipAndBorderColor(n, TOOL_TIP);
                });
                if (textArea.getText() == null || textArea.getText().trim().equals("")) {
                    valido = false;
                }
            } else if (n instanceof RadioButton) {
                RadioButton radio = (RadioButton) n;
                radio.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    ObservableList<Toggle> lista = radio.getToggleGroup().getToggles();
                    lista.forEach((t) -> {
                        RadioButton r = (RadioButton) t;
                        removeToolTipAndBorderColor(r, TOOL_TIP);
                    });
                });
                if (radio.getToggleGroup().getSelectedToggle() == null) {
                    valido = false;
                }
            }

            if (!valido) {
                failedFields.add(n);
                addToolTipAndBorderColor(n, TOOL_TIP);
            } else {
                removeToolTipAndBorderColor(n, TOOL_TIP);
            }

        }

        return failedFields.isEmpty();
    }

    /**
     * *******ADD AND REMOVE STYLES********
     */
    private static void addToolTipAndBorderColor(Node n, Tooltip t) {
        Tooltip.install(n, t);
        n.getStyleClass().add(ListaConstantesBase.EstiloCss.CAMPO_OBRIGATORIO.getValor());
    }

    private static void removeToolTipAndBorderColor(Node n, Tooltip t) {
        Tooltip.uninstall(n, t);
        n.getStyleClass().remove(ListaConstantesBase.EstiloCss.CAMPO_OBRIGATORIO.getValor());
    }

    /**
     * Remove as marcacoes de preenchimento obrigatorio do campos informado
     *
     * @param n Campo
     */
    public static void removeToolTipAndBorderColor(Node n) {
        Tooltip.uninstall(n, TOOL_TIP);
        n.getStyleClass().remove(ListaConstantesBase.EstiloCss.CAMPO_OBRIGATORIO.getValor());
    }

    /**
     * Verifica se os campos anotados para validacao de uma classe estao
     * preenchidos
     *
     * @param objeto Objeto que contem os atributos marcados para validacao
     * @return true se todos os campos marcados para validacao estiverem
     * preenchidos, false caso contrario
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static boolean validarCampos(FXMLBaseController objeto) throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = GeralUtil.obterListaAtributosPorAnotacao(objeto, ValidarCampo.class);
        return validarCampos(nodes.toArray(new Node[nodes.size()]));
    }

    /**
     * Remove as marcacoes de preenchimento obrigatoria de todos os campos
     * marcados para validacao.
     *
     * @param objeto Objeto que contem os atributos marcados para validacao
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void removerMarcacaoValidacao(Object objeto) throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = GeralUtil.obterListaAtributosPorAnotacao(objeto, ValidarCampo.class);
        nodes.forEach((node) -> {
            removeToolTipAndBorderColor(node);
        });

    }

    /**
     * Cria um Listener de controle para cada campo indicado por seu atributo
     * para manter uma mascara de entrada de dados
     *
     * @param controller Classe que representa o controller com os campos
     * indicado por seus atributos
     * @throws IllegalArgumentException Caso nao consiga fazer a leitura
     * reflection dos campos
     * @throws IllegalAccessException Caso nao consiga fazer a leitura
     * reflection dos campos
     */
    public static void criarListenerMascara(FXMLBaseController controller) throws IllegalArgumentException, IllegalAccessException {
        Class classe = controller.getClass();
        for (Field atributo : classe.getDeclaredFields()) {
            if (atributo.isAnnotationPresent(Mascara.class)) {
                Mascara m = atributo.getAnnotation(Mascara.class);
                atributo.setAccessible(true); // You might want to set modifier to public first.
                adicionarListener((Node) atributo.get(controller), m);
            }
        }
    }

    /**
     * Cria um listener de controle de mascara para o node informado
     *
     * @param node Objeto que representa um campo
     * @param mascara Classe de anotacao da mascara
     */
    public static void adicionarListener(Node node, Mascara mascara) {
        Integer tamanhoMax = GenericValidator.isInt(mascara.tamanhoMax()) ? Integer.valueOf(mascara.tamanhoMax()) : null;

        if (node instanceof TextField) {
            TextField textField = (TextField) node;
            switch (mascara.tipo()) {
                case TEXT_FIELD_VALOR:
                    textField.textProperty().addListener(new ListenerTextMoedaMascara(textField.textProperty(), tamanhoMax));
                    break;
                case TEXT_FIELD_APENAS_NUMERO:
                    textField.textProperty().addListener(new ListenerTextNumeroMascara(textField.textProperty(), tamanhoMax));
                    break;
                case TEXT_FIELD_MAXIMO_CARACTER:
                    textField.textProperty().addListener(new ListenerTextMaximoCaracteres(textField.textProperty(), tamanhoMax));
                    break;
                case TEXT_FIELD_CPF:
                    textField.textProperty().addListener(new ListenerTextCpfMascara(textField.textProperty()));
                    break;
                case TEXT_FIELD_CEP:
                    textField.textProperty().addListener(new ListenerTextCepMascara(textField.textProperty()));
                    break;
                case TEXT_FIELD_DATA:
                    textField.textProperty().addListener(new ListenerTextDataMascara(textField.textProperty()));
                    break;
                case TEXT_FIELD_CUSTOM:
                    textField.textProperty().addListener(new GenericListenerMascara<String>(textField.textProperty()) {
                        @Override
                        protected String getMascara() {
                            return mascara.custom();
                        }
                    });
                    break;
            }
        } else if (node instanceof DatePicker && mascara.tipo().equals(Mascara.TipoMascara.DATE_PICKER)) {
            DatePicker datePicker = (DatePicker) node;
            datePicker.getEditor().textProperty().addListener(new ListenerTextDataMascara(datePicker.getEditor().textProperty()));
        }
    }
}
