package com.taurus.util;

import com.taurus.util.annotation.Mascara;
import java.lang.annotation.Annotation;

/**
 *
 * @author Diego
 */
public class MascaraClasse implements Mascara {

    private TipoMascara tipo;
    private String tamanhoMax;
    private String custom;

    public MascaraClasse(TipoMascara tipo, String tamanhoMax, String custom) {
        this.tipo = tipo;
        this.tamanhoMax = tamanhoMax;
        this.custom = custom;
    }

    @Override
    public TipoMascara tipo() {
        return this.tipo;
    }

    @Override
    public String tamanhoMax() {
        return this.tamanhoMax;
    }

    @Override
    public String custom() {
        return this.custom;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return MascaraClasse.class;
    }

}
