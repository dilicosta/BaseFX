package com.taurus.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.text.MaskFormatter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.DateValidator;

/**
 * Classe com metodos utilitarios para formatar e converter objetos
 *
 * @author Diego Lima
 */
public class FormatarUtil {

    private final static String[] PALAVRAS_EXCECAO_UPERCASE = {"de", "da", "das", "do", "dos"};

    public final static String FORMATO_DATA = "dd/MM/yyyy";
    public final static String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm";
    public final static String FORMATO_DATA_HORA_SEG = "dd/MM/yyyy HH:mm:ss";
    public final static String FORMATO_DATA_HORA_MILI = "dd/MM/yyyy HH:mm:ss:SSS";
    public final static String FORMATO_HORA_MIN = "HH:mm";
    public final static String FORMATO_HORA_SEG = "HH:mm:ss";
    public final static String FORMATO_HORA_MILI = "HH:mm:ss:SSS";

    /**
     * Retorna o nome da Unidade Federativa do Brasil referente a sigla
     * informada. Caso nao encontre a UF associada a sigla, null sera retornado.
     *
     * @param siglaUf Sigla que representa a UF
     * @return Descricao da Sigla
     */
    public static String siglaUfDescricaoUf(String siglaUf) {
        if (siglaUf == null) {
            return null;
        }
        String sigla = siglaUf.toUpperCase();
        if (sigla.equals("AC")) {
            return "Acre";
        } else if (sigla.equals("AL")) {
            return "Alagoas";
        } else if (sigla.equals("AM")) {
            return "Amazonas";
        } else if (sigla.equals("AP")) {
            return "Amapá";
        } else if (sigla.equals("BA")) {
            return "Bahia";
        } else if (sigla.equals("CE")) {
            return "Ceará";
        } else if (sigla.equals("DF")) {
            return "Distrito Federal";
        } else if (sigla.equals("ES")) {
            return "Espírito Santo";
        } else if (sigla.equals("GO")) {
            return "Goiás";
        } else if (sigla.equals("MA")) {
            return "Maranhão";
        } else if (sigla.equals("MG")) {
            return "Minas Gerais";
        } else if (sigla.equals("MS")) {
            return "Mato Grosso do Sul";
        } else if (sigla.equals("MT")) {
            return "Mato Grosso";
        } else if (sigla.equals("PA")) {
            return "Pará";
        } else if (sigla.equals("PB")) {
            return "Paraíba";
        } else if (sigla.equals("PE")) {
            return "Pernambuco";
        } else if (sigla.equals("PI")) {
            return "Piauí";
        } else if (sigla.equals("PR")) {
            return "Paraná";
        } else if (sigla.equals("RJ")) {
            return "Rio de Janeiro";
        } else if (sigla.equals("RN")) {
            return "Rio Grande do Norte";
        } else if (sigla.equals("RO")) {
            return "Rondônia";
        } else if (sigla.equals("RR")) {
            return "Roraima";
        } else if (sigla.equals("RS")) {
            return "Rio Grande do Sul";
        } else if (sigla.equals("SC")) {
            return "Santa Catarina";
        } else if (sigla.equals("SE")) {
            return "Sergipe";
        } else if (sigla.equals("SP")) {
            return "São Paulo";
        } else if (sigla.equals("TO")) {
            return "Tocantins";
        } else {
            return null;
        }
    }

    /**
     * Converte uma data em String para um Calendar
     *
     * @param dataString data no formato String
     * @param formato formato em que a data esta na String
     * @return Calendar com a data correspondente
     */
    private static Calendar stringDataToCalendar(String dataString, String formato) {
        if (dataString == null) {
            return null;
        }
        Calendar data = Calendar.getInstance();
        Date dataDate = DateValidator.getInstance().validate(dataString, formato);
        if (dataDate == null) {
            return null;
        }
        data.setTime(dataDate);
        return data;
    }

    /**
     * Converte uma String data no formato dd/MM/yyyy HH:mm:ss em um Calendar
     *
     * @param dataHora data em String
     * @return Calendar
     */
    public static Calendar stringDataHoraToCalendar(String dataHora) {
        return FormatarUtil.stringDataToCalendar(dataHora, FORMATO_DATA_HORA);
    }

    /**
     * Converte uma String data no formato dd/MM/yyyy em um Calendar
     *
     * @param data data em String
     * @return Calendar
     */
    public static Calendar stringDataSimplesToCalendar(String data) {
        return FormatarUtil.stringDataToCalendar(data, FORMATO_DATA);
    }

    /**
     * Converte um Calendar em uma String data no formato dd/MM/yyyy HH:mm:ss
     *
     * @param dataHora data Calendar
     * @return data String
     */
    public static String dataHoraCaledarToString(Calendar dataHora) {
        if (dataHora == null) {
            return null;
        }
        return String.format("%1$td/%1$tm/%1$tY %1$tH:%1$tM:%1$tS", dataHora);
    }

    /**
     * Converte um Calendar em uma String data no formato dd/MM/yyyy
     *
     * @param data data Calendar
     * @return data String
     */
    public static String dataCaledarToString(Calendar data) {
        if (data == null) {
            return null;
        }
        return String.format("%1$td/%1$tm/%1$tY", data);
    }

    /**
     * Converte um Calendar em uma String data no formato dd/MM/yyyy
     *
     * @param data
     * @return data String
     */
    public static String dataDateToString(Date data) {
        if (data == null) {
            return null;
        }
        return String.format("%1$td/%1$tm/%1$tY", data);
    }

    /**
     * Converte um LocalDate em uma String data no formato dd/MM/yyyy
     *
     * @param data LocalDate
     * @return data String
     */
    public static String localDateToString(LocalDate data) {
        if (data == null) {
            return null;
        }
        return data.format(DateTimeFormatter.ofPattern(FORMATO_DATA));
    }

    /**
     * Converte um LocalDate em uma String data no formato dd/MM/yyyy
     *
     * @param data LocalDateTime
     * @param pattern formato
     * @return data String
     */
    public static String localDateTimeToString(LocalDateTime data, String pattern) {
        if (data == null) {
            return null;
        }
        return data.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Converte uma String data no formato dd/MM/yyyy em LocalDate
     *
     * @param data LocalDate
     * @return data String
     */
    public static LocalDate stringToLocalDate(String data) {
        try {
            return LocalDate.parse(data, DateTimeFormatter.ofPattern(FORMATO_DATA));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /**
     * Converte uma String data no formato do pattern em LocalDate
     *
     * @param data LocalDate
     * @param pattern formato da data
     * @return data String
     */
    public static LocalDate stringToLocalDate(String data, String pattern) {
        try {
            return LocalDate.parse(data, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /**
     * Converte uma String data no formato informado em LocalDate
     *
     * @param dataHora LocalDateTime
     * @param pattern padrao de formato
     * @return data String
     */
    public static LocalDateTime stringToLocalDateTime(String dataHora, String pattern) {
        try {
            return LocalDateTime.parse(dataHora, DateTimeFormatter.ofPattern(pattern));
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    /**
     * Retorna um tempo representado em milisegundos em uma String no seguinte
     * formatto: HH:mm:ss:SSS
     *
     * @param milisegundos
     * @return String no formato "HH:mm:ss:SSS
     */
    public static String formatarTempoMilisegundos(long milisegundos) {
        long mili = milisegundos % 1000;
        long seg = (milisegundos / 1000) % 60;
        long min = (milisegundos / (60 * 1000)) % 60;
        long hora = milisegundos / (3600 * 1000);

        if (seg >= 60) {
            seg = seg % 60;
        }
        return FormatarUtil.lpad(hora, 2) + ":" + FormatarUtil.lpad(min, 2) + ":" + FormatarUtil.lpad(seg, 2) + ":" + FormatarUtil.lpad(mili, 3);
    }

    /**
     * Formata o numero do telefone informado de acordo com a quantidade de
     * digitos
     *
     * @param telefone
     * @return String telefone formatada
     */
    public static String formatarTelefone(String telefone) {
        if (GenericValidator.isBlankOrNull(telefone)) {
            return "";
        }
        String pattern = null;
        int tamanho = telefone.length();
        switch (tamanho) {
            case 8:
                pattern = "####-####";
                break;
            case 9:
                pattern = "#####-####";
                break;
            case 10:
                pattern = "(##) ####-####";
                break;
            case 11:
                pattern = "(##) #####-####";
                break;
            default:
                pattern = "";
        }
        return formatarString(telefone, pattern);
    }

    /**
     * Formata uma string de acordo com a mascara informada
     *
     * Mascaras: # - numeros ' - escapar caracteres especiais U - Letras em
     * maiusculo L - Letras em minusculo A - Letras e numeros ? - Letras * -
     * Qualquer coisa H - Hex caracteres (0-9, a-f, A-F)
     *
     * @param value valor da string a ser formatada
     * @param pattern mascara
     * @return String formatada
     */
    public static String formatarString(String value, String pattern) {
        if (GenericValidator.isBlankOrNull(value)) {
            return "";
        }
        if (GenericValidator.isBlankOrNull(pattern)) {
            return value;
        }

        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setPlaceholderCharacter('_');
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }

    public static String formatarCep(String uf) {
        return formatarString(uf, "##.###-###");
    }

    public static String formatarCpf(String uf) {
        return formatarString(uf, "###.###.###-##");
    }

    public static String formatarMoedaReal(Object valor) {
        if (valor == null) {
            return "";
        }
        return NumberFormat.getCurrencyInstance().format(valor);
    }

    public static String formatarNumero(Object valor) {
        if (valor == null) {
            return "";
        }
        return NumberFormat.getNumberInstance().format(valor);
    }

    /**
     * Retorna uma String do numero informado com uma quantidade minima de
     * digitos, preenchendo com zero caso necessario
     *
     * @param numero
     * @param qtdDigitos
     * @return
     */
    public static String lpad(Long numero, int qtdDigitos) {
        return String.format("%0" + qtdDigitos + "d", numero);
    }

    /**
     *  * Retorna uma String do numero informado com uma quantidade minima de
     * digitos, preenchendo com zero caso necessario
     *
     * @param numero
     * @param qtdDigitos
     * @return
     */
    public static String lpad(Integer numero, int qtdDigitos) {
        return lpad(new Long(numero), qtdDigitos);
    }

    /**
     * Retorna um valor numerico de uma string formatada
     *
     * @param string String com o valor numerico
     * @return Float se conseguir obter um valor numerico da string
     */
    public static Float getNumero(String string) {
        if (!GenericValidator.isBlankOrNull(string)) {
            // remove todos os "."
            // substitui a "," por "."
            // remove tudo que não for numero ou ","
            // Ex: "R$ 1.430,25" = "1430.25"
            String valorStr = string.replaceAll("\\.", "").replaceFirst(",", ".").replaceAll("[^0-9.-]", "");
            if (GenericValidator.isFloat(valorStr)) {
                return Float.valueOf(valorStr);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Retorna um BigDecimal com 2 casas decimais
     *
     * @param valor
     * @return
     */
    public static BigDecimal getBigDecimalMoeda(String valor) {
        if (valor == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd;
    }

    /**
     * Retorna um BigDecimal com 2 casas decimais
     *
     * @param valor
     * @return
     */
    public static BigDecimal getBigDecimalMoeda(Float valor) {
        if (valor == null) {
            return null;
        }
        return getBigDecimalMoeda(valor.toString());
    }

    /**
     * Remove todos os caracteres que nao sejam numeros
     *
     * @param numeroComMascara
     * @return
     */
    public static String removerMascaraNumero(String numeroComMascara) {
        if (numeroComMascara == null) {
            return null;
        }
        return numeroComMascara.replaceAll("[^0-9]", "");
    }

    public static String iniciaisMaiusculas(String expressao) {
        if (GenericValidator.isBlankOrNull(expressao)) {
            return expressao;
        }
        StringBuilder sb = new StringBuilder();
        for (String palavra : expressao.trim().split(" ")) {
            if (isPalavraExcecao(palavra)) {
                sb.append(" ").append(palavra.toLowerCase());
            } else {
                sb.append(" ").append(StringUtils.capitalize(palavra.toLowerCase()));
            }
        }
        return sb.toString().trim();
    }

    private static boolean isPalavraExcecao(String palavra) {
        for (String palavraExecao : PALAVRAS_EXCECAO_UPERCASE) {
            if (palavra.toLowerCase().equals(palavraExecao)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LocalDateTime hora = stringToLocalDateTime("01:34", FORMATO_HORA_MIN);
        System.out.println(localDateTimeToString(hora, FORMATO_DATA_HORA_MILI));
        
        System.out.println(iniciaisMaiusculas("RAMON LUIZ DE FREITAS CAMELO"));
    }
}
