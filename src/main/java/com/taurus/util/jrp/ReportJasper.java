package com.taurus.util.jrp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import com.taurus.exception.NegocioException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe ReportJasper utilizada para gerar relatorios a partir de uma Colecao
 * de JavaBeans utilizando o Jasper Reports
 *
 * @author Diego Lima
 * @version ver 2.1 - 12/05/2018
 */
public class ReportJasper {

    private static final Log LOG = LogFactory.getLog(ReportJasper.class);

    // Modificacao para inputStream pois o java nao encontra o jasperfile dentro de um jar
    public static byte[] runPdf(InputStream jasperInputStream, Map parametros, List beans) throws NegocioException {
        LOG.debug("Gerando PDF...");
        byte[] bytes = null;
        try {
            bytes = JasperRunManager.runReportToPdf(jasperInputStream, parametros, new JRBeanCollectionDataSource(beans));
        } catch (Exception ex) {
            LOG.error("Erro ao gerar o relatório.", ex);
            throw new NegocioException("Erro ao gerar o arquivo PDF!", ex);
        }
        return bytes;
    }

    public static byte[] runXls(File jasperFile, Map parametros, JRDataSource pJrDataSource) throws NegocioException {
        JasperPrint jasperPrint;
        try {
            LOG.debug("Gerando XLS...");
            jasperPrint = JasperFillManager.fillReport(jasperFile.getPath(), parametros, pJrDataSource);
        } catch (JRException ex) {
            LOG.error("", ex);
            throw new NegocioException("Erro ao gerar o arquivo JasperPrint!", ex);
        }

        JRXlsExporter exporter = new JRXlsExporter();

        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);

        try {
            exporter.exportReport();
        } catch (JRException ex) {
            LOG.error("", ex);
            throw new NegocioException("Erro ao exportar o JasperPrint para XLS!", ex);
        }

        return (xlsReport.toByteArray());
    }

    public static String runHtml(String pJasperFile, Map pParameters, JRDataSource pJrDataSource)
            throws NegocioException {
        File reportFile = new File(pJasperFile);

        String htmlFile = "rptBftAtendimentoByComarca.html";
        LOG.debug("Gerando html...");

        try {
            JasperRunManager.runReportToHtmlFile(reportFile.getPath(),
                    "/web1/www/htdocs/servicos/di/tmp/" + htmlFile,
                    pParameters,
                    pJrDataSource);
        } catch (JRException ex) {
            LOG.error("Erro ao gerar o arquivo html!", ex);
            throw new NegocioException("Erro ao gerar o arquivo html!", ex);
        }

        return "/servicos/di/tmp/" + htmlFile;
    }

    public static void geraPdf(String pJasperFile, String pJasperPrintFile, Map pParameters, JRDataSource pJrDataSource)
            throws NegocioException {

        long start = System.currentTimeMillis();
        java.io.File jpFile = new java.io.File(pJasperFile);
        java.io.File prFile = new java.io.File(pJasperPrintFile);

        try {
            LOG.debug("Preenchendo o relatório...");
            /* Preenche o relatório com os dados. Gera o arquivo rptViagemByMotivo.jrprint */
            JasperFillManager.fillReportToFile(jpFile.getPath(), pParameters, pJrDataSource);

            LOG.debug("Exportando o relatório...");
            /* Exporta para o formato PDF */
            JasperExportManager.exportReportToPdfFile(prFile.getPath());
        } catch (JRException ex) {
            LOG.error("", ex);
            throw new NegocioException("Não foi possível Gerar o Relatório PDF.", ex);
        }

        long difTime = (System.currentTimeMillis() - start) / 1000;
        LOG.debug("Tempo de execução (s): " + difTime);
    }

}
