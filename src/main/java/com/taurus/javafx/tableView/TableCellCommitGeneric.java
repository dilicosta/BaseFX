package com.taurus.javafx.tableView;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;

/**
 * Classe utilizada para confirmar ou nao a atualizacao de um campo em um
 * TableView.
 *
 * Ex.: colValor.setOnEditCommit(new
 * TableCellCommitGeneric<GenericLinhaView, Float>(TableCellCommitGeneric.Tipo.NUMERO_FLOAT,
 * false))
 *
 *
 * @author Diego
 * @param <S> Tipo da linha do TableView
 * @param <V> Tipo de retorno do novo valor
 */
public abstract class TableCellCommitGeneric<S, V> implements EventHandler<TableColumn.CellEditEvent<S, V>> {

    private boolean permitirNull;

    public abstract void atualizar(S rowValue, V novoValor, V valorAntigo, boolean valido);

    public TableCellCommitGeneric(boolean permitirNull) {
        this.permitirNull = permitirNull;
    }

    @Override
    public void handle(TableColumn.CellEditEvent<S, V> event) {
        if (permitirNull && event.getNewValue() == null) {
            atualizar(event.getRowValue(), event.getNewValue(), event.getOldValue(), true);
        } else {
            boolean valido = event.getNewValue() != null;
            atualizar(event.getRowValue(), event.getNewValue(), event.getOldValue(), valido);
        }
    }

}
