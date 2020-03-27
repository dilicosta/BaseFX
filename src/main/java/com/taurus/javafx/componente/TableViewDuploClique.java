package com.taurus.javafx.componente;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author Diego
 * @param <T> Tipo Generico da linha da tabela
 */
public abstract class TableViewDuploClique<T> implements Callback<TableView<T>, TableRow<T>> {

    private boolean atualizar = false;
    private boolean remover = false;

    public TableViewDuploClique() {
    }

    public TableViewDuploClique(boolean atualizar) {
        this.atualizar = atualizar;
    }

    @Override
    public TableRow<T> call(TableView<T> tableView) {
        TableRow<T> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                T linhaView = row.getItem();
                T linhaViewAtualizada = eventoDuploClique(linhaView);
                if (this.atualizar) {
                    tableView.getItems().set(row.getIndex(), linhaViewAtualizada);
                }
                if (this.remover) {
                    tableView.getItems().remove(row.getIndex());
                }
            }
        });
        return row;
    }

    public void atualizarLinha() {
        this.atualizar = true;
    }

    public void removerLinha() {
        this.remover = true;
    }

    public abstract T eventoDuploClique(T linhaView);

}
