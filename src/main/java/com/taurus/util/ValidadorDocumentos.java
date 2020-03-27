package com.taurus.util;

import org.apache.commons.validator.GenericValidator;

/**
 * Classe utilizada para metodos de validacao de documentos.
 *
 * @author Renato Marotta
 * @since 09/12/2009
 */
public abstract class ValidadorDocumentos {

    /**
     * Metodo que testa a integridade de um cpf ou de um cnpj
     *
     * @param v o cpf ou CNPJ que se deseja testar, contendo apenas algarismos
     * @return true se for um cpf valido, false caso contrario
     */
    public static boolean validarCPFCNPJ(String v) {
        if (!GenericValidator.isLong(v)) {
            return false;
        }
        if (v.length() == 11) {
            return validarCPF(v);
        }
        if (v.length() == 14) {
            return validarCNPJ(v);
        }
        return false;
    }

    /**
     * Metodo que verifica se um CPF informado e valido. Este metodo tambem
     * verifica se a String informada e um numero valido. O parametro deve ser
     * recebido no padrao XXXXXXXXXXX (11 digitos numericos)
     *
     * @param cpf CPF que se deseja validar, contendo apenas algarismos
     * @return true se for um cpf valido, false caso contrario
     */
    public static boolean validarCPF(String cpf) {
        if (!GenericValidator.isLong(cpf)) {
            return false;
        }
        try {
            if (cpf.length() != 11) {
                return false;
            }
            char[] digitos = cpf.toCharArray();

            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (10 - i) * Integer.parseInt("" + digitos[i]);
            }
            soma = 11 - (soma - ((soma / 11) * 11));
            if ((soma == 10) || (soma == 11)) {
                soma = 0;
            }
            if (soma != Integer.parseInt("" + digitos[9])) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (11 - i) * Integer.parseInt("" + digitos[i]);
            }
            soma = 11 - (soma - ((soma / 11) * 11));
            if ((soma == 10) || (soma == 11)) {
                soma = 0;
            }
            return soma == Integer.parseInt("" + digitos[10]);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo que verifica se um CNPJ informado e valido. Este metodo tambem
     * verifica se a String informada e um numero valido. O parametro deve ser
     * recebido no padrao XXXXXXXXXXXXXX (14 digitos numericos)
     *
     * @param cnpj CNPJ que se deseja validar, contendo apenas algarismos
     * @return true se for um CNPJ valido, false caso contrario
     */
    public static boolean validarCNPJ(String cnpj) {
        if (!GenericValidator.isLong(cnpj)) {
            return false;
        }
        try {
            if (cnpj.length() != 14) {
                return false;
            }
            char[] digitos = cnpj.toCharArray();
            int pesos[] = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += pesos[i + 1] * Integer.parseInt("" + digitos[i]);
            }
            soma = 11 - (soma - ((soma / 11) * 11));
            if ((soma == 10) || (soma == 11)) {
                soma = 0;
            }
            if (soma != Integer.parseInt("" + digitos[12])) {
                return false;
            }

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += pesos[i] * Integer.parseInt("" + digitos[i]);
            }
            soma = 11 - (soma - ((soma / 11) * 11));
            if ((soma == 10) || (soma == 11)) {
                soma = 0;
            }
            return soma == Integer.parseInt("" + digitos[13]);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo que faz a validacao de um titulo de eleitor.
     *
     * @param titulo n�mero do titulo de eleitor recebido para validacao
     * @return true se for uma titulo de eleitor valido, false caso contrario
     */
    public static boolean validarTituloEleitor(String titulo) throws NumberFormatException {
        if (!GenericValidator.isLong(titulo)) {
            return false;
        }
        if (titulo.length() < 12) {
            return validarTituloEleitor("0" + titulo);
        }
        if (titulo.length() > 12) {
            return false;
        }

        int estado = Integer.parseInt(titulo.substring(8, 10));
        if (estado < 1 || estado > 28) {
            return false;
        }

        int soma = 0;
        for (int i = 0; i < 8; i++) {
            soma += Integer.parseInt(titulo.substring(i, i + 1)) * (9 - i);
        }
        int resto = soma % 11;
        int digito1;
        switch (resto) {
            case 0:
                if (estado == 1 || estado == 2) {
                    digito1 = 1;
                } else {
                    digito1 = 0;
                }
                break;
            case 1:
                digito1 = 0;
                break;
            default:
                digito1 = 11 - resto;
                break;
        }

        if (digito1 != Integer.parseInt(titulo.substring(10, 11))) {
            return false;
        }

        soma = 0;
        for (int i = 8; i < 10; i++) {
            soma += Integer.parseInt(titulo.substring(i, i + 1)) * (12 - i);
        }
        soma += digito1 * 2;
        resto = soma % 11;
        int digito2;
        switch (resto) {
            case 0:
                if (estado == 1 || estado == 2) {
                    digito2 = 1;
                } else {
                    digito2 = 0;
                }
                break;
            case 1:
                digito2 = 0;
                break;
            default:
                digito2 = 11 - resto;
                break;
        }

        return digito2 == Integer.parseInt(titulo.substring(11, 12));
    }

    /**
     * Metodo que valida o valor de um numero PIS. Este metodo tambem verifica
     * se a String informada e um numero valido.
     *
     * @param pis n�mero de cadastro no PIS que sera validado
     * @return true se o valor e valido, false caso contrario.
     */
    public static boolean validarPIS(String pis) throws NumberFormatException {
        if (!GenericValidator.isLong(pis)) {
            return false;
        }
        if (pis.length() != 11) {
            return false;
        }
        int wdv = Integer.parseInt(pis.substring(pis.length() - 1, pis.length()));
        int wsoma = 0;
        int wm11 = 2;
        for (int i = 0; i < 10; i++) {
            wsoma += wm11 * Integer.parseInt(pis.substring(9 - i, 10 - i));
            if (wm11 < 9) {
                wm11++;
            } else {
                wm11 = 2;
            }
        }
        int wdigito = 11 - (wsoma % 11);
        if (wdigito > 9) {
            wdigito = 0;
        }
        return wdv == wdigito;
    }
}
