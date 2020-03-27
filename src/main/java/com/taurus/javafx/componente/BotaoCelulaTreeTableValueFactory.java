package com.taurus.javafx.componente;

import com.sun.prism.impl.Disposer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

/**
 *
 * @author Diego
 */
public class BotaoCelulaTreeTableValueFactory implements Callback<TreeTableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>> {

    @Override
    public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<Disposer.Record, Boolean> param) {
        return new SimpleBooleanProperty(param.getValue() != null);
    }
}
