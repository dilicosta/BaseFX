package com.taurus.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Diego
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mascara {

    TipoMascara tipo();

    String tamanhoMax() default "";
    /**
     * Defina uma mascara
     * @return 
     */
    String custom() default "";

    public enum TipoMascara {
        DATE_PICKER,
        TEXT_FIELD_APENAS_NUMERO,
        TEXT_FIELD_VALOR,
        TEXT_FIELD_MAXIMO_CARACTER,
        TEXT_FIELD_DATA,
        TEXT_FIELD_CEP,
        TEXT_FIELD_CPF,
        TEXT_FIELD_CUSTOM;
    }
}
