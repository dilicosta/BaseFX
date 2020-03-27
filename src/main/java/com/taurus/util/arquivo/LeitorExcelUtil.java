package com.taurus.util.arquivo;

import com.taurus.exception.ValidacaoException;
import com.taurus.util.FormatarUtil;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Diego
 */
public class LeitorExcelUtil {

    private Workbook workbook;

    public LeitorExcelUtil(File file) throws IOException, InvalidFormatException {
        this.workbook = WorkbookFactory.create(file);
    }

    @Override
    public void finalize() throws IOException, Throwable {
        try {
            this.fecharArquivo();
        } finally {
            super.finalize();
        }
    }

    public Integer getTotalLinhas() {
        return this.workbook.getSheetAt(0).getLastRowNum() + 1;
    }

    public Integer getTotalColunas(int linha) {
        Row row = this.workbook.getSheetAt(0).getRow(linha);
        if (row == null) {
            return null;
        } else {
            return Integer.valueOf(row.getLastCellNum());
        }
    }

    public String getCellString(int linha, int coluna) {
        Cell celula = this.getCell(linha, coluna);
        if (celula == null) {
            return null;
        }

        switch (celula.getCellTypeEnum()) {
            case STRING:
                return celula.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(celula)) {
                    return FormatarUtil.dataDateToString(celula.getDateCellValue());
                } else {
                    Double valor = celula.getNumericCellValue();
                    if (this.contemFracao(valor)) {
                        return valor.toString();
                    } else {
                        return String.valueOf(valor.intValue());
                    }
                }
            case BOOLEAN:
                return String.valueOf(celula.getBooleanCellValue());
            case FORMULA:
                return celula.getCellFormula();
            default:
                return "";
        }
    }

    public LocalDate getCellLocalDate(int linha, int coluna) throws DataInvalida {
        Cell celula = this.getCell(linha, coluna);
        if (celula == null) {
            return null;
        }
        switch (celula.getCellTypeEnum()) {
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(celula)) {
                    try {
                        return LocalDate.ofEpochDay(celula.getDateCellValue().getTime());
                    } catch (Exception ex) {
                        throw new DataInvalida();
                    }
                } else {
                    throw new DataInvalida();
                }
            default:
                throw new DataInvalida();
        }
    }

    public LocalDate getCellLocalDate(int linha, int coluna, String pattern) throws DataInvalida {
        Cell celula = this.getCell(linha, coluna);
        if (celula == null) {
            return null;
        }
        switch (celula.getCellTypeEnum()) {
            case STRING:
                try {
                    return FormatarUtil.stringToLocalDate(celula.getStringCellValue(), pattern);
                } catch (Exception ex) {
                    throw new DataInvalida();
                }
            default:
                throw new DataInvalida();
        }
    }

    public Integer getCellInteger(int linha, int coluna) {
        Cell celula = this.getCell(linha, coluna);
        if (celula == null) {
            return null;
        }
        Double valor = this.getCellDouble(linha, coluna);
        return valor == null ? null : valor.intValue();
    }

    public Double getCellDouble(int linha, int coluna) {
        Cell celula = this.getCell(linha, coluna);
        if (celula == null) {
            return null;
        }
        switch (celula.getCellTypeEnum()) {
            case NUMERIC:
                return celula.getNumericCellValue();
            default:
                return null;
        }
    }

    private Cell getCell(int linha, int coluna) {
        return this.workbook.getSheetAt(0).getRow(linha).getCell(coluna);
    }

    public String getCellTipo(int linha, int coluna) {
        Cell celula = this.getCell(linha, coluna);
        if (celula != null) {
            return celula.getCellTypeEnum().name();
        } else {
            return null;
        }
    }

    public List<String> getValoresColuna(int indiceLinhaInicial, int indiceColuna) throws ValidacaoException {
        int totalLinhas = this.getTotalLinhas();
        if (totalLinhas < indiceLinhaInicial) {
            throw new ValidacaoException("O índice da linha inicial é menor do que o número total de linhas do arquivo excel.");
        }

        List<String> valoresColuna = new ArrayList<>();
        for (int linha = indiceLinhaInicial; linha < totalLinhas; linha++) {
            String valor = this.getCellString(linha, indiceColuna);
            valoresColuna.add(valor);
        }
        return valoresColuna;
    }

    public void fecharArquivo() throws IOException {
        this.workbook.close();
    }

    private boolean contemFracao(Double valor) {
        Double valorFracionado = valor - valor.intValue();
        return valorFracionado > 0;
    }

    public static class DataInvalida extends Exception {

        public DataInvalida() {
            super("Data inválida na celula do excel.");
        }
    }
}
