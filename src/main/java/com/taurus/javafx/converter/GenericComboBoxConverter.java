package com.taurus.javafx.converter;

import com.taurus.util.GeralUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javafx.util.StringConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Diego
 * @param <T> Tipo do Ojbeto que sera exibido no ComboBox
 */
public class GenericComboBoxConverter<T> extends StringConverter<T> {

    private static final Log LOG = LogFactory.getLog(GenericComboBoxConverter.class);

    private Map<String, T> objetos = new HashMap();
    private String nomeatributoDescricao;

    /**
     *
     * @param nomeatributoDescricao nome do atributo que contem o valor a ser
     * exibido em cada item do ComboBox
     */
    public GenericComboBoxConverter(String nomeatributoDescricao) {
        this.nomeatributoDescricao = nomeatributoDescricao;
    }

    @Override
    public String toString(T objeto) {
        if (objeto == null) {
            return null;
        }
        String chave = null;
        String atributos[] = nomeatributoDescricao.split("\\.");
        Object obj = objeto;
        for (String atributo : atributos) {
            try {
                obj = GeralUtil.getFieldInReflection(obj, atributo);
            }catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                LOG.error("Erro ao buscar o valor do objeto para ser exibido no ComboBox", ex);
                return "";
            }
        }
        chave = (String) obj;
        objetos.put(chave, objeto);
        return chave;
    }

    @Override
    public T fromString(String chave) {
        return objetos.get(chave);
    }
}
