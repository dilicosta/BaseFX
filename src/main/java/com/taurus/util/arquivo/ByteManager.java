package com.taurus.util.arquivo;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe responsavel pela leitura de arquivos em disco e geracao de hash
 *
 * @author Diego Lima
 */
public class ByteManager {

    private static final Log LOG = LogFactory.getLog(ByteManager.class);

    public static byte[] readFile(File file) throws IOException {
        return FileUtils.readFileToByteArray(file);
    }

    public static byte[] readFile(String pathName) throws IOException {
        File file = new File(pathName.trim());
        return readFile(file);
    }

    public static File writeFile(String diretorio, String nomeArquivo, byte[] byteArquivo) throws IOException {
        File file = new File(diretorio, nomeArquivo);
        return writeFile(file, byteArquivo);
    }

    public static File writeFile(File file, byte[] byteArquivo) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileUtils.writeByteArrayToFile(file, byteArquivo);
        return file;
    }

    public static boolean isFileExists(String pathName) {
        File file = new File(pathName.trim());
        return file.exists();
    }

    /**
     * Retorna o Hash dos bytes no formato hexadecimal
     *
     * @param dados bytes que deseja gerar o Hash
     * @return Uma string com a Hash dos bytes informados
     */
    public static String getSHA256Hash(byte dados[]) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return ByteManager.getHexCodes(md.digest(dados));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("erro ao gerar hash SHA-256", ex);
        }
    }

    /**
     * Converte uma sequencia de bytes para hexadecimal
     *
     * @param bytes Sequencia de bytes que para serem convertidos
     * @return Uma string com a sequencia hexadecimal dos bytes informados
     */
    public static String getHexCodes(byte[] bytes) {

        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));

        }
        return hexString.toString();

    }

    public static void main(String args[]) throws IOException {

        String teste = "Java Cryptography Architecture";
        System.out.println(getSHA256Hash(teste.getBytes()));
        writeFile("/teste1/tete2/","teste.txt", teste.getBytes());

    }
}
