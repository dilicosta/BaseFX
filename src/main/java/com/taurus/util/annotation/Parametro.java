package com.taurus.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Identifica os atributos que são utilizados para receber parametros de um
 * controller para outro.
 * Estes atributos serao limpos automaticamente quando a janela for fechada.
 *
 * @author Diego Lima
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parametro {

}
