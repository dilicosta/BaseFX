package com.taurus.javafx.componente;

import com.taurus.javafx.ds.GenericLinhaView;
import javafx.scene.control.ListCell;

/**
 * Classe utilizada para exibir itens em um ListView e configurar um evento para
 * o duplo clique em um item da lista
 *
 * @author Diego
 * @param <T> Tipo Generico que representa o item da lista.
 */
public abstract class GenericListCell<T> extends ListCell<GenericLinhaView<T>> {

    @Override
    public void updateItem(GenericLinhaView<T> item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            this.setText(item.getColuna1());
            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!this.isEmpty())) {
                    eventoDuploClique(item);
                }
            });
        } else {
            this.setText(null);
        }
    }

    protected abstract void eventoDuploClique(GenericLinhaView<T> item);
}
