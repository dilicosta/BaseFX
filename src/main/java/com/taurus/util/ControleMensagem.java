package com.taurus.util;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Classe responsavel por gerenciar todas as mensagens do sistema
 *
 * @author Diego Lima
 */
public class ControleMensagem implements Serializable {

    private final ResourceBundle mensagens;
    private final MessageFormat formatter = new MessageFormat("");
    private static ControleMensagem instancia;

    private ControleMensagem() {
        mensagens = ResourceBundle.getBundle("mensagens");
    }

    public static ControleMensagem getInstance() {
        if (instancia == null) {
            instancia = new ControleMensagem();
        }
        return instancia;
    }

    /**
     * Retorna a descricao da mensagem dado sua chave e incluindo os paramentros
     * informados
     *
     * @param chaveMensagem nome da chave que identifica a mensagem
     * @param parametros Nao obrigatorio. Parametros que podem ser incluidos na
     * mensagem. Podem ser texto livre ou chaves do arquivo bundle
     * @return Mensagem formtada com seus parametros se houver
     */
    public String getMensagem(String chaveMensagem, String... parametros) {
        try {
            formatter.applyPattern(mensagens.getString(chaveMensagem));
        } catch (Exception ex) {
            return chaveMensagem;
        }
        String[] parametrosNormalizados = this.normalizarParametros(parametros);
        return formatter.format(parametrosNormalizados);
    }

    /**
     * Verifica se o parametro eh uma chave e a utiliza, senao, utiliza o valor
     * informado
     *
     * @param parametros
     * @return
     */
    private String[] normalizarParametros(String[] parametros) {
        String[] parametrosNormalizados = new String[parametros.length];
        for (int i = 0; i < parametros.length; i++) {
            try {
                parametrosNormalizados[i] = mensagens.getString(parametros[i]);
            } catch (Exception e) {
                parametrosNormalizados[i] = parametros[i];
            }
        }
        return parametrosNormalizados;
    }
}
