package com.taurus.javafx.componente;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author Diego
 * @param <T> Tipo Generico da linha da tabela
 */
public abstract class TableViewCliqueSimples<T> implements Callback<TableView<T>, TableRow<T>> {

    @Override
    public TableRow<T> call(TableView<T> param) {
        TableRow<T> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && (!row.isEmpty())) {
                T linhaView = row.getItem();
                eventoClique(linhaView);
            }
        });
        return row;
    }

    public abstract void eventoClique(T linhaView);

}
