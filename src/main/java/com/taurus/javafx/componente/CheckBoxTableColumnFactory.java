package com.taurus.javafx.componente;

import com.taurus.javafx.tableView.LinhaViewComCheckBox;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author Diego
 * @param <T> Objeto que representa a linha da tabela
 */
public abstract class CheckBoxTableColumnFactory<T extends LinhaViewComCheckBox> implements Callback<TableColumn<T , Boolean>, TableCell<T , Boolean>> {

    public CheckBoxTableColumnFactory() {
    }

    @Override
    public TableCell<T, Boolean> call(TableColumn<T, Boolean> param) {
        return new TableCell<T, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                T linhaView = empty ? null : this.getTableView().getItems().get(this.getIndex());
                if (!empty && exibirItem(linhaView)) {
                    setGraphic(this.criarCheckBox());
                } else {
                    setGraphic(null);
                }
            }

            private CheckBox criarCheckBox() {
                T linhaView = super.getTableView().getItems().get(this.getIndex());
                CheckBox checkBox = new CheckBox();
                //checkBox.setPrefSize(15, 25);
                checkBox.selectedProperty().bindBidirectional(linhaView.selectedProperty());
                checkBox.setOnAction((ActionEvent a) -> {
                    CheckBoxTableColumnFactory.this.eventoCheckBox(a, linhaView);
                });
                return checkBox;
            }
        };
    }

    public abstract void eventoCheckBox(ActionEvent actionEvent, T linhaView);

    protected abstract boolean exibirItem(T linhaView);
}
