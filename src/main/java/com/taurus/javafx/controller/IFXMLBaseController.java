package com.taurus.javafx.controller;

import javafx.scene.Node;
import javafx.stage.Stage;

public interface IFXMLBaseController {

    public Node getView();

    /**
     * executa instrucoes gerais ao reinicializar uma janela
     */
    public void reinicializarJanelaBase();

    public Stage getStage();

    public void setStage(Stage stage);

    /**
     * Executado ao abrir a janela
     */
    public abstract void aoAbrirJanela();

    public void aoFecharJanela();
    
    public boolean isNovaJanela();
    public void setNovaJanela(boolean novaJanela);
}
