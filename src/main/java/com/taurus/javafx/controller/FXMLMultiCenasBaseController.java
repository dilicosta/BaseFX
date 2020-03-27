package com.taurus.javafx.controller;

import com.taurus.javafx.util.ValidarCampoUtil;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Classe de controle utilizada em janelas do tipo "avancar" e "voltar" com
 * varias cenas.
 *
 * @author Diego Lima
 */
public abstract class FXMLMultiCenasBaseController extends FXMLBaseController implements IFXMLMultiCenasBaseController {

    protected String fxmlFilePathCenas;
    private String[] listaFxmlFilePath;
    private Node[] listaView;
    private int indiceCena = 0;

    @Override
    public final void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        inicializarArrays();
        listaView[0] = this.getView();
        for (int i = 1; i < listaFxmlFilePath.length; i++) {
            try (InputStream fxmlStream = getClass().getResourceAsStream(listaFxmlFilePath[i])) {
                FXMLLoader loader = new FXMLLoader();
                loader.setController(this);
                this.indiceCena = i;
                listaView[i] = (loader.load(fxmlStream));
                ValidarCampoUtil.criarListenerMascara(this);
            }
        }
        this.indiceCena = 0;
    }

    @Override
    public final void reinicializarJanelaEspecifico() {
        this.indiceCena = 0;
        this.getStage().setScene(this.getView().getScene());
        reinicializarJanelaMultiCenas();
    }

    private void inicializarArrays() {
        String senas[] = this.fxmlFilePathCenas.split(",");
        this.listaFxmlFilePath = new String[senas.length];
        this.listaView = new Node[senas.length];
        for (int i = 0; i < senas.length; i++) {
            this.listaFxmlFilePath[i] = senas[i].trim();
        }
    }

    @Override
    public final int getIndiceCena() {
        return this.indiceCena;
    }

    @Override
    public final void setIndiceCena(int indiceCena) {
        this.indiceCena = indiceCena;
    }

    @Override
    public final Node[] getListaView() {
        return this.listaView;
    }

    @FXML
    public final void avancar(ActionEvent actionEvent) {
        if (this.validarAvancar()) {
            this.cj.abrirProximaCena(this);
        }
    }

    @FXML
    public final void voltar(ActionEvent actionEvent) {
        if (this.validarVoltar()) {
            this.cj.abrirCenaAnterior(this);
        }
    }

    /**
     * Reinicializa todos os parametros da janela e volta para a primeira cena
     */
    protected void resetJanela() {
        this.reinicializarJanelaBase();
        this.cj.abrirCena(this, 0);
    }

    public abstract void setFxmlFilePathCenas(String fxmlFilePathCenas);

    public abstract void reinicializarJanelaMultiCenas();

    public abstract boolean validarAvancar();

    public abstract boolean validarVoltar();
}
