package com.taurus.javafx.converter;

import com.taurus.util.ListaConstantesBase.EstadoBrasil;
import javafx.util.StringConverter;

/**
 *
 * @author Diego
 */
public class EstadoBrasilConverter extends StringConverter<EstadoBrasil> {

    @Override
    public String toString(EstadoBrasil estadoBrasil) {
        return estadoBrasil == null ? null : estadoBrasil.getNome();
    }

    @Override
    public EstadoBrasil fromString(String string) {
        for (EstadoBrasil estadoBrasil : EstadoBrasil.values()) {
            if (estadoBrasil.getNome().equals(string)) {
                return estadoBrasil;
            }
        }
        return null;
    }

}
