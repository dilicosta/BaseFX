package com.taurus.javafx.converter;

import com.taurus.util.ListaConstantesBase.EstadoCivil;
import javafx.util.StringConverter;

/**
 *
 * @author Diego
 */
public class EstadoCivilConverter extends StringConverter<EstadoCivil> {

    @Override
    public String toString(EstadoCivil estadoCivil) {
        return estadoCivil == null ? null : estadoCivil.getDescricao();
    }

    @Override
    public EstadoCivil fromString(String string) {
        for (EstadoCivil ec : EstadoCivil.values()) {
            if (ec.getDescricao().equals(string)) {
                return ec;
            }
        }
        return null;
    }
}
