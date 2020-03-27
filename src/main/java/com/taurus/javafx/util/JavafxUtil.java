package com.taurus.javafx.util;

import com.taurus.entidade.BaseEntityID;
import com.taurus.javafx.tableView.LinhaViewComCheckBox;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Diego
 */
public class JavafxUtil {

    /**
     * Retorna o bean da lista correspondente ao bean selecionado no ComboBox
     *
     * @param <T> Tipo generico do bean
     * @param comboBox
     * @param lista lista com todos os beans presentes no ComboBox
     * @return bean corresponde ou null se nao existir.
     */
    public static <T extends BaseEntityID> T getItemSelecionado(ComboBox<T> comboBox, List<T> lista) {
        T itemSelecionado = comboBox.getValue();
        if (itemSelecionado != null) {
            for (T item : lista) {
                if (item.getId().equals(itemSelecionado.getId())) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * Forca o tootip a aparecer mais rapido
     *
     * @param tooltip
     */
    public static void acelerarExibicaoTooltip(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(5)));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            System.out.println("Erro na criação de Tooltip Rápido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Integer getInteger(TextField textField) {
        if (textField == null || GenericValidator.isBlankOrNull(textField.getText())) {
            return null;
        }
        try {
            return Integer.valueOf(textField.getText().trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Float getFloat(TextField textField) {
        if (textField == null || GenericValidator.isBlankOrNull(textField.getText())) {
            return null;
        }
        try {
            return Float.valueOf(textField.getText().trim().replaceFirst(",", "."));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * Retorna um BigDecimal com 2 casas decimais do valor contido no TextField
     *
     * @param textField
     * @return Retorna um BigDecimal ou null se o valor do TextField nao for
     * numerico
     */
    public static BigDecimal getBigDecimal(TextField textField) {
        if (textField == null || GenericValidator.isBlankOrNull(textField.getText())) {
            return null;
        }
        String valorStr = textField.getText().trim().replaceFirst(",", ".");
        if (GenericValidator.isFloat(valorStr)) {
            BigDecimal bd = new BigDecimal(valorStr);
            return bd.setScale(2);
        } else {
            return null;
        }
    }

    public static CheckBox criarSelectAllCheckBox(ObservableList<? extends LinhaViewComCheckBox> lista) {
        CheckBox selectAllCheckBox = new CheckBox();
        selectAllCheckBox.setOnAction((ActionEvent event) -> {
            lista.forEach((linhaView) -> {
                linhaView.setSelected(selectAllCheckBox.isSelected());
            });
        });
        return selectAllCheckBox;
    }
}
