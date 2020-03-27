package com.taurus.javafx.controller;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Interface de controle utilizada em janelas do tipo "avancar" e "voltar" com
 * varias cenas.
 *
 * @author Diego Lima
 */
public interface IFXMLMultiCenasBaseController {

    public int getIndiceCena();

    public void setIndiceCena(int indiceCena);

    public Node[] getListaView();

    public abstract void aoAbrirCena(int indiceCena);

    public Stage getStage();
}
