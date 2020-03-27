package com.taurus.util;

import java.io.FileOutputStream;
import com.itextpdf.text.Chunk;

import com.itextpdf.text.Document;

import com.itextpdf.text.Image;

import com.itextpdf.text.PageSize;

import com.itextpdf.text.Paragraph;

import com.itextpdf.text.Phrase;

import com.itextpdf.text.pdf.BarcodeEAN;

import com.itextpdf.text.pdf.PdfContentByte;

import com.itextpdf.text.pdf.PdfWriter;

public class TesteCodigoBarras {

    @SuppressWarnings("static-access")

    public static void main(String args[]) {


        // criando um objeto da classe Document
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        try {

            //Aqui começamos a utilizar as classes do iText: o documento
            //criado acima será
            //direcionado para um arquivo PDF.
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D://Codigo_Barra_Java_Linha_Codigo.pdf"));

            //abrindo o documento.
            document.open();

            //adicionando um novo paragrafo.
            document.add(new Paragraph("CÓDIGOS DE BARRA"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            //Comecando a configurar o cod de barras
            PdfContentByte cb = writer.getDirectContent();

            BarcodeEAN codeEAN = new BarcodeEAN();

            //O iText suporta os principais tipos de código de barra, como Barcode39,
            //  Barcode128 (128, 128_UCC, 128_RAW),  BarcodeEAN (EAN13, EAN8, UPCA, UPCE), EANSUP, etc
            codeEAN.setCodeType(codeEAN.EAN13);
            String codigo = "789532641098";
            String codigoComDV = gerarDVEAN13(codigo);

            codeEAN.setCode(codigoComDV);

            Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);

            document.add(new Phrase(new Chunk(imageEAN, 0, 0)));

        } catch (Exception de) {
            de.printStackTrace();
        }
        document.close();
    }

    private static String gerarDVEAN13(String codigo) {
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

        int dv = 10 - (somaFinal % 10);

        System.out.println("Codigo: "+codigo);
        System.out.println("Numeros impares: " + sbNumerosImpares.toString());
        System.out.println("Soma impares: " + somaNumerosImpares);
        System.out.println("Numeros pares: " + sbNumerosPares.toString());
        System.out.println("Soma pares: " + somaNumerosPares);

        System.out.println("\nSoma par x 3: " + somaFinal);
        System.out.println("DV par x 3: " + dv);

        return codigo + dv;
    }
}
