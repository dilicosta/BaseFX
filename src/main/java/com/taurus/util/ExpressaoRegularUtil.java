package com.taurus.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author Diego
 */
public class ExpressaoRegularUtil {

    private final String mascara;
    private static final List<String> CARACTERES_ESPECIAIS = Arrays.asList("*", ".", "+", "$", "[", "]", "(", ")", "{", "}", "\\", "|");
    private List<ComposicaoExpressaoRegular> listaComposicaoER = new ArrayList();
    private List<String> listaExpressaoRegularParcial = new ArrayList();
    private String expressaoRegularValidacaoParcial;
    private String expressaoRegularValidacaoFinal;

    public ExpressaoRegularUtil(String mascara) {
        this.mascara = mascara;
        this.construirExpressoesRegulares();
    }

    public String getMascara() {
        return mascara;
    }

    /**
     * Retorna uma expressao regular que valida todas as partes da construcao do
     * texto de acordo com a mascara
     *
     * Ex: mascara = "99/99", valida = "", "2", "23", "23/", "23/1" e "23/10".
     *
     * @return expressao regular
     */
    public String getExpressaoRegularValidacaoParcial() {
        return expressaoRegularValidacaoParcial;
    }

    /**
     * Retorna uma expressao regular que valida o texto final.
     *
     * Ex: mascara "99/99", valida apenas = "23/10".
     *
     * @return expressao regular
     */
    public String getExpressaoRegularValidacaoFinal() {
        return expressaoRegularValidacaoFinal;
    }

    /**
     * Retorna uma lista de expressoes regulares. Cada expressao valida valida
     * uma parte da construcao do texto.
     *
     * @return lista de expressoes regulares
     */
    public List<String> getListaExpressaoRegularParcial() {
        return listaExpressaoRegularParcial;
    }

    private void construirExpressoesRegulares() {
        this.gerarListaERParcial();
        this.construirExpressaoRegularValidacaoParcial();
        this.construirExpressaoRegularValidacaoFinal();

    }

    private void gerarListaERParcial() {
        if (!GenericValidator.isBlankOrNull(mascara)) {
            ComposicaoExpressaoRegular.Tipo tipo = ComposicaoExpressaoRegular.getTipo(String.valueOf(mascara.charAt(0)));
            int tamanho = 0;
            int i;
            boolean primeiroCaracter = true;
            for (i = 0; i < mascara.length(); i++) {
                String c = String.valueOf(mascara.charAt(i));
                ComposicaoExpressaoRegular.Tipo tipoTemp = ComposicaoExpressaoRegular.getTipo(c);

                if (tipoTemp == tipo) {
                    // caracter diferente
                    if (!primeiroCaracter && tipo == ComposicaoExpressaoRegular.Tipo.CARACTER && !c.equals(String.valueOf(mascara.charAt(i - 1)))) {
                        this.listaComposicaoER.add(new ComposicaoExpressaoRegular(tipo, tamanho, String.valueOf(mascara.charAt(i - 1))));
                        tamanho = 1;
                    } else {
                        tamanho++;
                    }
                } else {
                    if (tipo == ComposicaoExpressaoRegular.Tipo.CARACTER) {
                        this.listaComposicaoER.add(new ComposicaoExpressaoRegular(tipo, tamanho, String.valueOf(mascara.charAt(i - 1))));
                    } else {
                        this.listaComposicaoER.add(new ComposicaoExpressaoRegular(tipo, tamanho, null));
                    }
                    tamanho = 1;
                    tipo = tipoTemp;
                }
                primeiroCaracter = false;
            }
            if (tipo == ComposicaoExpressaoRegular.Tipo.CARACTER) {
                this.listaComposicaoER.add(new ComposicaoExpressaoRegular(tipo, tamanho, String.valueOf(mascara.charAt(i - 1))));
            } else {
                this.listaComposicaoER.add(new ComposicaoExpressaoRegular(tipo, tamanho, null));
            }
        }
    }

    private void construirExpressaoRegularValidacaoParcial() {
        StringBuilder expReg = new StringBuilder();
        StringBuilder expRegAcumulada = new StringBuilder();
        boolean primeiro = true;
        for (int i = 0; i < this.listaComposicaoER.size(); i++) {
            StringBuilder expRegParcial = new StringBuilder();
            ComposicaoExpressaoRegular er = listaComposicaoER.get(i);

            if (null != er.getTipo()) {
                switch (er.getTipo()) {
                    case CARACTER:
                        if (ExpressaoRegularUtil.isCaracterEspecial(er.getCaracter())) {
                            expRegParcial.append("\\");
                        }
                        expRegParcial.append(er.getCaracter());
                        break;
                    case NUMERO:
                        expRegParcial.append("[0-9]");
                        break;
                    case LETRA_MINUSCULA:
                        expRegParcial.append("[a-z]");
                        break;
                    case LETRA_MAIUSCULA_MINUSCULA:
                        expRegParcial.append("[a-zA-Z]");
                        break;
                    default:
                        break;
                }
            }

            if (primeiro) {
                expRegParcial.append("{0,").append(er.tamanho).append("}");

            } else {
                if (er.tamanho > 1) {
                    expRegParcial.append("{1,").append(er.tamanho).append("}");
                }
            }
            expReg.append(expRegAcumulada);
            expReg.append(expRegParcial);
            listaExpressaoRegularParcial.add(expRegAcumulada.toString() + expRegParcial.toString());

            expRegAcumulada.append(expRegParcial.toString().replaceAll("\\{[01],", "{"));

            if (i + 1 != this.listaComposicaoER.size()) {
                expReg.append("|");
            }
            primeiro = false;
        }
        this.expressaoRegularValidacaoParcial = expReg.toString();
    }

    private void construirExpressaoRegularValidacaoFinal() {
        if (this.expressaoRegularValidacaoParcial == null) {
            this.expressaoRegularValidacaoFinal = null;
        } else {
            String erNaoPipeEspecial = "[^\\\\]\\|";
            String[] expressoesParciais = this.expressaoRegularValidacaoParcial.split(erNaoPipeEspecial);
            this.expressaoRegularValidacaoFinal = expressoesParciais[expressoesParciais.length - 1].replaceAll("\\{[01],", "{");
        }
    }

    public static boolean isCaracterEspecial(String caracter) {
        return CARACTERES_ESPECIAIS.contains(caracter);
    }

    private static class ComposicaoExpressaoRegular {

        private final Tipo tipo;
        private final int tamanho;
        private final String caracter;

        public ComposicaoExpressaoRegular(Tipo tipo, int tamanho, String caracter) {
            this.tipo = tipo;
            this.tamanho = tamanho;
            this.caracter = caracter;
        }

        public Tipo getTipo() {
            return tipo;
        }

        public int getTamanho() {
            return tamanho;
        }

        public String getCaracter() {
            return caracter;
        }

        public static Tipo getTipo(String c) {
            if (c.matches("9")) {
                return Tipo.NUMERO;
            } else if (c.matches("a")) {
                return Tipo.LETRA_MINUSCULA;
            } else if (c.matches("A")) {
                return Tipo.LETRA_MAIUSCULA_MINUSCULA;
            } else {
                return Tipo.CARACTER;
            }
        }

        public static enum Tipo {
            NUMERO, LETRA_MINUSCULA, LETRA_MAIUSCULA_MINUSCULA, CARACTER;
        }
    }

    public static void main(String args[]) {
        String mascara = "999.9%99.99|9-9dd";
        String teste = "013.4%65.646-6dd";
        ExpressaoRegularUtil erUtil = new ExpressaoRegularUtil(mascara);
        for (Object er : erUtil.listaComposicaoER) {
            ComposicaoExpressaoRegular exp = (ComposicaoExpressaoRegular) er;
            System.out.println(exp.getTipo() + " - " + exp.tamanho + " caracter[" + exp.getCaracter() + "]");
        }
        String expressao = erUtil.getExpressaoRegularValidacaoParcial();

        StringBuilder sb = new StringBuilder();
        System.out.println("\"\" - " + "".matches(expressao));
        for (int i = 0; i < teste.length(); i++) {
            sb.append(teste.charAt(i));
            System.out.println(sb.toString() + " - " + sb.toString().matches(expressao));
        }

        System.out.println("Expressão Parciais:" + expressao);
        System.out.println("Expressão Final:" + erUtil.getExpressaoRegularValidacaoFinal());
        System.out.println("Expressões parciais:");
        for (String exp : erUtil.getListaExpressaoRegularParcial()) {
            System.out.println(exp);
        }
        
        System.out.println(erUtil.listaComposicaoER.size() +" - "+ erUtil.listaExpressaoRegularParcial.size());
    }
}
