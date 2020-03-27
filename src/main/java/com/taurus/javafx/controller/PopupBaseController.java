package com.taurus.javafx.controller;

import com.taurus.util.ListaConstantesBase;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Diego
 */
public abstract class PopupBaseController extends FXMLBaseController implements IPopupBaseController{

    @FXML
    Button btnFechar;

    @Override
    public final void initialize(URL location, ResourceBundle resources) {
        this.btnFechar.getStyleClass().add(ListaConstantesBase.EstiloCss.BOTAO_FECHAR.getValor());
        inicializar(location, resources);
    }

    @FXML
    @Override
    public final void fechar(ActionEvent actionEvent) {
        this.aoFecharJanela();
        this.getStage().close();
    }

    protected abstract void inicializar(URL location, ResourceBundle resources);
}
