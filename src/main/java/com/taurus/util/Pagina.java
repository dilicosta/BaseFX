package com.taurus.util;

/**
 *
 * @author Diego
 */
public class Pagina {

    private Integer numeroPagina;
    private Integer itensPorPagina;
    private Long totalItens;

    public Pagina() {
        super();
    }

    public Pagina(Integer numeroPagina, Integer itensPorPagina, Long totalItens) {
        this.numeroPagina = numeroPagina;
        this.itensPorPagina = itensPorPagina;
        this.totalItens = totalItens;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public int getItensPorPagina() {
        return itensPorPagina;
    }

    public void setItensPorPagina(Integer itensPorPagina) {
        this.itensPorPagina = itensPorPagina;
    }

    public Long getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(Long total) {
        this.totalItens = total;
    }

    public int getNumeroPaginas() {
        if (itensPorPagina != null && totalItens != null) {
            Long numPag = totalItens % itensPorPagina == 0 ? (totalItens / itensPorPagina) : (totalItens / itensPorPagina) + 1;
            return numPag > 1 ? numPag.intValue() : 1;
        } else {
            return 1;
        }
    }

}
