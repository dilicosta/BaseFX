package com.taurus.javafx.converter;

import com.taurus.util.ListaConstantesBase.SimNao;
import javafx.util.StringConverter;

/**
 *
 * @author Diego
 */
public class SimNaoConverter extends StringConverter<SimNao> {

    @Override
    public String toString(SimNao simNao) {
        return simNao == null ? null : simNao.getDescricao();
    }

    @Override
    public SimNao fromString(String string) {
        for (SimNao sn : SimNao.values()) {
            if (sn.getDescricao().equals(string)) {
                return sn;
            }
        }
        return null;
    }
}
