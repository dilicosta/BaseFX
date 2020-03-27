package com.taurus.javafx.tableView;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * Classe que controla a exibicao do dado na celula de um TableView. Utilizada
 * para celulas com edicao para exibir o novo valou ou nao.
 *
 * Ex. colValor.setCellFactory(col -> new
 * TableCellEditFactoryGeneric<GenericLinhaView, String>())
 *
 * @author Diego
 * @param <S>
 * @param <T>
 */
public abstract class TableCellEditFactoryGeneric<S, T> extends TextFieldTableCell<S, T> {
    
    private boolean permitirNull;
    private Tipo tipo;
    
    public static enum Tipo {
        NUMERO_FLOAT, NUMERO_INTEGER, DATA_CALENDAR, STRING
    }
    
    public TableCellEditFactoryGeneric(boolean permitirNull, Tipo tipo) {
        this.permitirNull = permitirNull;
        StringConverter<T> converter;
        switch (tipo) {
            case DATA_CALENDAR:
                converter = (StringConverter<T>) new DateStringConverter("dd/MM/yyyy");
                break;
            case NUMERO_FLOAT:
                converter = (StringConverter<T>) new FloatStringConverter();
                break;
            case NUMERO_INTEGER:
                converter = (StringConverter<T>) new IntegerStringConverter();
                break;
            default:
                converter = (StringConverter<T>) new DefaultStringConverter();
        }
        this.setConverter(converter);
    }
    
    public TableCellEditFactoryGeneric(boolean permitirNull, StringConverter<T> converter) {
        super(converter);
        this.permitirNull = permitirNull;
    }
    
    @Override
    public void updateItem(T item, boolean empty) {
        if (empty) {
            super.updateItem(item, empty);
        } else {
            if (item == null) {
                if (permitirNull) {
                    super.updateItem(item, empty);
                } else {
                    super.updateItem(getItem(), empty);
                }
            } else {
                if (this.isValido(item)) {
                    super.updateItem(item, empty);
                } else {
                    super.updateItem(getItem(), empty);
                }
            }
        }
    }
    
    public abstract boolean isValido(T item);
}
