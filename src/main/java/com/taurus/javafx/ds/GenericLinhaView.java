package com.taurus.javafx.ds;

import com.taurus.javafx.tableView.LinhaViewComCheckBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe generica que representa uma linha no TableView
 *
 * @author Diego Linha
 * @param <T> Tipo Generico do Objeto da Linha
 */
public class GenericLinhaView<T> extends LinhaViewComCheckBox{

    private T objeto;

    private String coluna1;
    private String coluna2;
    private String coluna3;
    private String coluna4;
    private String coluna5;
    private String coluna6;
    private String coluna7;
    private String coluna8;
    private String coluna9;
    private String coluna10;
    private String coluna11;
    private String coluna12;
    private String coluna13;
    private String coluna14;
    private String coluna15;

    public GenericLinhaView(T objeto) {
        this.objeto = objeto;
    }

    public T getBean() {
        return objeto;
    }

    public String getColuna1() {
        return coluna1;
    }

    public void setColuna1(String coluna1) {
        this.coluna1 = coluna1;
    }

    public String getColuna2() {
        return coluna2;
    }

    public void setColuna2(String coluna2) {
        this.coluna2 = coluna2;
    }

    public String getColuna3() {
        return coluna3;
    }

    public void setColuna3(String coluna3) {
        this.coluna3 = coluna3;
    }

    public String getColuna4() {
        return coluna4;
    }

    public void setColuna4(String coluna4) {
        this.coluna4 = coluna4;
    }

    public String getColuna5() {
        return coluna5;
    }

    public void setColuna5(String coluna5) {
        this.coluna5 = coluna5;
    }

    public String getColuna6() {
        return coluna6;
    }

    public void setColuna6(String coluna6) {
        this.coluna6 = coluna6;
    }

    public String getColuna7() {
        return coluna7;
    }

    public void setColuna7(String coluna7) {
        this.coluna7 = coluna7;
    }

    public String getColuna8() {
        return coluna8;
    }

    public void setColuna8(String coluna8) {
        this.coluna8 = coluna8;
    }

    public String getColuna9() {
        return coluna9;
    }

    public void setColuna9(String coluna9) {
        this.coluna9 = coluna9;
    }

    public String getColuna10() {
        return coluna10;
    }

    public void setColuna10(String coluna10) {
        this.coluna10 = coluna10;
    }

    public String getColuna11() {
        return coluna11;
    }

    public void setColuna11(String coluna11) {
        this.coluna11 = coluna11;
    }

    public String getColuna12() {
        return coluna12;
    }

    public void setColuna12(String coluna12) {
        this.coluna12 = coluna12;
    }

    public String getColuna13() {
        return coluna13;
    }

    public void setColuna13(String coluna13) {
        this.coluna13 = coluna13;
    }

    public String getColuna14() {
        return coluna14;
    }

    public void setColuna14(String coluna14) {
        this.coluna14 = coluna14;
    }

    public String getColuna15() {
        return coluna15;
    }

    public void setColuna15(String coluna15) {
        this.coluna15 = coluna15;
    }

    public static <T> List<GenericLinhaView<T>> toList(List<T> listaBean) {
        if (listaBean == null) {
            return null;
        } else {
            List<GenericLinhaView<T>> listaGenerica = new ArrayList<>();
            for (T bean : listaBean) {
                listaGenerica.add(new GenericLinhaView<>(bean));
            }
            return listaGenerica;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.objeto);
        hash = 31 * hash + Objects.hashCode(this.coluna1);
        hash = 31 * hash + Objects.hashCode(this.coluna2);
        hash = 31 * hash + Objects.hashCode(this.coluna3);
        hash = 31 * hash + Objects.hashCode(this.coluna4);
        hash = 31 * hash + Objects.hashCode(this.coluna5);
        hash = 31 * hash + Objects.hashCode(this.coluna6);
        hash = 31 * hash + Objects.hashCode(this.coluna7);
        hash = 31 * hash + Objects.hashCode(this.coluna8);
        hash = 31 * hash + Objects.hashCode(this.coluna9);
        hash = 31 * hash + Objects.hashCode(this.coluna10);
        hash = 31 * hash + Objects.hashCode(this.coluna11);
        hash = 31 * hash + Objects.hashCode(this.coluna12);
        hash = 31 * hash + Objects.hashCode(this.coluna13);
        hash = 31 * hash + Objects.hashCode(this.coluna14);
        hash = 31 * hash + Objects.hashCode(this.coluna15);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GenericLinhaView<?> other = (GenericLinhaView<?>) obj;
        if (!Objects.equals(this.coluna1, other.coluna1)) {
            return false;
        }
        if (!Objects.equals(this.coluna2, other.coluna2)) {
            return false;
        }
        if (!Objects.equals(this.coluna3, other.coluna3)) {
            return false;
        }
        if (!Objects.equals(this.coluna4, other.coluna4)) {
            return false;
        }
        if (!Objects.equals(this.coluna5, other.coluna5)) {
            return false;
        }
        if (!Objects.equals(this.coluna6, other.coluna6)) {
            return false;
        }
        if (!Objects.equals(this.coluna7, other.coluna7)) {
            return false;
        }
        if (!Objects.equals(this.coluna8, other.coluna8)) {
            return false;
        }
        if (!Objects.equals(this.coluna9, other.coluna9)) {
            return false;
        }
        if (!Objects.equals(this.coluna10, other.coluna10)) {
            return false;
        }
        if (!Objects.equals(this.coluna11, other.coluna11)) {
            return false;
        }
        if (!Objects.equals(this.coluna12, other.coluna12)) {
            return false;
        }
        if (!Objects.equals(this.coluna13, other.coluna13)) {
            return false;
        }
        if (!Objects.equals(this.coluna14, other.coluna14)) {
            return false;
        }
        if (!Objects.equals(this.coluna15, other.coluna15)) {
            return false;
        }
        if (!Objects.equals(this.objeto, other.objeto)) {
            return false;
        }
        return true;
    }
}
