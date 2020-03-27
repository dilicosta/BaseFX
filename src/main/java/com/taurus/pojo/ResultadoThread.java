package com.taurus.pojo;

/**
 *
 * @author Diego
 */
public class ResultadoThread {

    private boolean sucesso;
    private String mensagem;
    private String detalheMensagem;

    public ResultadoThread(boolean sucesso, String mensagem, String detalheMensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.detalheMensagem = detalheMensagem;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDetalheMensagem() {
        return detalheMensagem;
    }

    public void setDetalheMensagem(String detalheMensagem) {
        this.detalheMensagem = detalheMensagem;
    }
}
