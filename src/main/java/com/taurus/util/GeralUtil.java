package com.taurus.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.validator.GenericValidator;

/**
 * Classe com metodos de utilidade geral
 *
 * @author Diego Lima
 */
public class GeralUtil {

    /**
     * Retorna o valor de um atributo em um objeto utilizando Reflection. Os
     * nomes dos metodos de acesso ao atributo devem seguir o padrao java beans.
     *
     * @param objeto Objeto que possui o atributo
     * @param nomeAtributo nome do atributo
     * @return Valor do atributo.
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static Object getFieldInReflection(Object objeto, String nomeAtributo) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class objectClass = objeto.getClass();
        Method metodo = objectClass.getMethod("get" + nomeAtributo.substring(0, 1).toUpperCase() + nomeAtributo.substring(1));
        return metodo.invoke(objeto);
    }

    /**
     * Atribui o valor a um atributo em um objeto utilizando Reflection. Os
     * nomes dos metodos de acesso ao atributo devem seguir o padrao java beans.
     *
     * @param objeto Objeto que possui o atributo
     * @param nome nome do atributo
     * @param valor valor do atributo
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void setFieldInReflection(Object objeto, String nome, Object valor) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        Class objectClass = objeto.getClass();
        Field field = objectClass.getDeclaredField(nome);
        Method metodo = objectClass.getMethod("set" + nome.substring(0, 1).toUpperCase() + nome.substring(1), field.getType());
        metodo.invoke(objeto, valor);
    }

    /**
     * Retorna a mensagem da causa original do erro
     *
     * @param ex Objeto que representa a excecao
     * @return mensagem original do erro
     */
    public static String getMensagemOriginalErro(Throwable ex) {
        return ex.getMessage() + "\r\n" + getMensagemOriginalErro(ex, 1);
    }

    /**
     * * Metodo recursivo para encontrar a mensagem original do erro. Pesquisa
     * o maximo de 10 causas
     *
     * @param ex Objeto que representa a excecao
     * @param i numero de profundidade na busca da causa original
     * @return Mensagem do erro original ou do nivel 10
     */
    private static String getMensagemOriginalErro(Throwable ex, int i) {
        if (i <= 10) {
            i++;
        }
        if (ex.getCause() == null || i > 10) {
            return ex.getMessage();
        } else {
            return getMensagemOriginalErro(ex.getCause(), i);
        }
    }

    /**
     * Retorna uma lista dos atributos que contem determinada anotacao
     *
     * @param classeInstanciada Objeto que contem os atributos
     * @param classeAnotacao Classe que representa a anotacao que deve ser
     * procurada nos atributos
     * @return Lista de objetos instanciados que contem a anotacao indicada
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static List obterListaAtributosPorAnotacao(Object classeInstanciada, Class classeAnotacao) throws IllegalArgumentException, IllegalAccessException {
        Class classe = classeInstanciada.getClass();
        List objetos = new ArrayList();
        for (Field atributo : classe.getDeclaredFields()) {
            if (atributo.isAnnotationPresent(classeAnotacao)) {
                atributo.setAccessible(true); // You might want to set modifier to public first.
                objetos.add(atributo.get(classeInstanciada));
            }
        }
        return objetos;
    }

    public static List<String> obterListaNomesAtributosPorAnotacao(Object classeInstanciada, Class classeAnotacao) throws IllegalArgumentException, IllegalAccessException {
        Class classe = classeInstanciada.getClass();
        List<String> listaNomes = new ArrayList();
        for (Field atributo : classe.getDeclaredFields()) {
            if (atributo.isAnnotationPresent(classeAnotacao)) {
                listaNomes.add(atributo.getName());
            }
        }
        return listaNomes;
    }

    /**
     * Retorna uma lista com os objetos informados Obs: O metodo
     * Arrays.asList(T) retorna uma lista fixa por isso a criacao deste metodo.
     *
     * @param <T> Tipo generico da lista
     * @param objetos lista de objetos ou objeto unico
     * @return nova lista de objetos
     */
    public static <T> List<T> criarLista(T... objetos) {
        List<T> lista = new ArrayList();
        lista.addAll(Arrays.asList(objetos));
        return lista;
    }

    public static String getAno(Calendar data) {
        return String.format("%1$ty", data);
    }

    public static String getMes(Calendar data) {
        return String.format("%1$tm", data);
    }

    public static Calendar getDataProximoMes(Calendar dataBase, int numMeses) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(dataBase.getTime());
        c.add(Calendar.MONTH, numMeses);
        return c;
    }

    /**
     * Calcula a diferença de tempo entre dois tempos distintos e retorna em uma
     * String
     *
     * @param tempoInicial
     * @param tempoFinal
     * @return String no formato "HH:mm:ss:SSS" ou null caso seja impossivel
     * calcular
     */
    public static String calcularDiferencaTempo(LocalDateTime tempoInicial, LocalDateTime tempoFinal) {
        if (tempoInicial == null || tempoFinal == null || tempoInicial.isAfter(tempoFinal)) {
            return null;
        }
        long hora = ChronoUnit.HOURS.between(tempoInicial, tempoFinal);
        long min = (ChronoUnit.MINUTES.between(tempoInicial, tempoFinal) % 60);
        long seg = (ChronoUnit.SECONDS.between(tempoInicial, tempoFinal) % 60);
        if (seg >= 60) {
            seg = seg % 60;
        }
        long mili = ChronoUnit.MILLIS.between(tempoInicial, tempoFinal) % 1000;
        return FormatarUtil.lpad(hora, 2) + ":" + FormatarUtil.lpad(min, 2) + ":" + FormatarUtil.lpad(seg, 2) + ":" + FormatarUtil.lpad(mili, 3);
    }

    /**
     * Insere o tempo informado na variavel LocalDateTime Formatos de tempo
     * aceitos: hh:mm, hh:mm:ss, hh:mm:ss:SSS
     *
     * @param localDateTime
     * @param tempo
     * @return LocalDateTime com o tempo inserido ou null caso a hora seja
     * invalida
     */
    public static LocalDateTime adicionarTempoNaData(LocalDateTime localDateTime, String tempo) {
        if (tempo.length() < 5 || localDateTime == null) {
            return null;
        }
        try {
            if (tempo.length() >= 5) {
                int hora = Integer.valueOf(tempo.substring(0, 2));
                int min = Integer.valueOf(tempo.substring(3, 5));
                localDateTime = localDateTime.withHour(hora).withMinute(min).withSecond(0).withNano(0);
            }
            if (tempo.length() >= 8) {
                int seg = Integer.valueOf(tempo.substring(6, 8));
                localDateTime = localDateTime.withSecond(seg).withNano(0);
            }
            if (tempo.length() == 12) {
                int mili = Integer.valueOf(tempo.substring(9, 12));
                localDateTime = localDateTime.withNano(mili * 1000000);
            }
            return localDateTime;
        } catch (Exception ex) {
            return null;
        }
    }

    public static LocalDateTime adicionarTempoNaData(LocalDate localDate, String tempo) {
        return adicionarTempoNaData(localDate.atStartOfDay(), tempo);
    }

    public static LocalDateTime adicionarTempoNaDataAtual(String tempo) {
        return adicionarTempoNaData(LocalDate.now(), tempo);
    }

    public static void main(String[] args) {
        LocalDateTime tempoInicial = FormatarUtil.stringToLocalDateTime("05/05/2018 11:33:01:001", FormatarUtil.FORMATO_DATA_HORA_MILI);
        LocalDateTime tempoFinal = FormatarUtil.stringToLocalDateTime("06/05/2018 11:33:01:000", FormatarUtil.FORMATO_DATA_HORA_MILI);
        String diferencaTempo = calcularDiferencaTempo(tempoInicial, tempoFinal);
        System.out.println("Diferença: " + diferencaTempo);
        long mili = ChronoUnit.MILLIS.between(tempoInicial, tempoFinal);
        System.out.println("Diferença mili: " + mili);
        System.out.println("Diferença formatada: " + FormatarUtil.formatarTempoMilisegundos(mili));
        //System.out.println("Diferença: ")
        //System.out.println(FormatarUtil.localDateTimeToString(adicionarTempoNaDataAtual("23:41"), FormatarUtil.FORMATO_DATA_HORA_MILI));
    }

    /**
     * Retorna uma string com o tamanho maximo informado ou o tamanho maximo da
     * string, o que for menor.
     *
     * @param valor
     * @param tamanhoMaximo
     * @return
     */
    public static String getTamanhoMaximo(String valor, int tamanhoMaximo) {
        if (GenericValidator.isBlankOrNull(valor)) {
            return valor;
        } else {
            tamanhoMaximo = tamanhoMaximo > valor.length() ? valor.length() : tamanhoMaximo;
            return valor.substring(0, tamanhoMaximo);
        }
    }
}
