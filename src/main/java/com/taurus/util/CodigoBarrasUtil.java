package com.taurus.util;

import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Diego
 */
public class CodigoBarrasUtil {

    /**
     * Retorna o digito verificador para o codigo de barras padrao EAN 13
     *
     * @param codigo codigo base com 12 digitos
     * @return digito verificar
     * @exception IllegalArgumentException caso o codigo informado nao seja
     * numerico nem contenha 12 digitos
     */
    public static String gerarDvEAN13(String codigo) {
        if (codigo.length() != 12 || !GenericValidator.isLong(codigo)) {
            throw new IllegalArgumentException("O código de barras deve conter 12 digitos numericos.");
        }
        StringBuilder sbNumerosPares = new StringBuilder();
        StringBuilder sbNumerosImpares = new StringBuilder();
        int somaNumerosPares = 0;
        int somaNumerosImpares = 0;
        for (int i = 0; i < codigo.length(); i++) {
            char c = codigo.charAt(i);
            // o indice eh par, mas a sequencia do codigo eh impar
            if (i % 2 == 0) {
                sbNumerosImpares.append(c);
                somaNumerosImpares += Integer.valueOf(String.valueOf(c));
            } else {
                sbNumerosPares.append(c);
                somaNumerosPares += Integer.valueOf(String.valueOf(c));
            }
        }
        int parX3 = somaNumerosPares * 3;
        int somaFinal = parX3 + somaNumerosImpares;

        int mod10 = (somaFinal % 10);
        int dv = mod10 == 0 ? 0 : 10 - mod10;
        return String.valueOf(dv);
    }

}
