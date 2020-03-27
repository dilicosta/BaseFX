package com.taurus.javafx.controller;

import com.taurus.javafx.util.ControleJanela;
import com.taurus.util.ControleMensagem;
import com.taurus.util.GeralUtil;
import com.taurus.javafx.util.ValidarCampoUtil;
import com.taurus.util.annotation.Parametro;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fxutils.maskedtextfield.MaskedTextField;
import org.springframework.beans.factory.InitializingBean;

public abstract class FXMLBaseController implements IFXMLBaseController, InitializingBean, Initializable {

    private static final Log LOG = LogFactory.getLog(FXMLBaseController.class);

    protected ControleJanela cj = ControleJanela.getInstance();
    protected ControleMensagem cm = ControleMensagem.getInstance();
    protected boolean novaJanela;

    public FXMLBaseController() {
        super();
    }

    private Stage stage;
    protected Node view;
    protected String fxmlFilePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        // Carrega o arquivo FXML da interface e sua classe controller
        loadFXML();
        // Cria Listener de controle para manter uma mascara de entrada de dados nos campos indicados por seus atributos
        ValidarCampoUtil.criarListenerMascara(this);
    }

    private void loadFXML() throws IOException {
        InputStream fxmlStream = getClass().getResourceAsStream(fxmlFilePath);
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        this.view = (loader.load(fxmlStream));
    }

    @Override
    public final Node getView() {
        return view;
    }

    /**
     * executa instrucoes gerais ao reinicializar uma janela
     */
    @Override
    public final void reinicializarJanelaBase() {
        limparCamposFXML();

        try {
            ValidarCampoUtil.removerMarcacaoValidacao(this);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            LOG.error(cm.getMensagem("erro_limpar_marcacao_validacao_campos"), ex);
        }

        reinicializarJanelaEspecifico();

    }

    /**
     * Limpa todos os campos do formulario indicados pela anotacao FXML
     */
    private void limparCamposFXML() {
        try {
            List<Object> listaCampo = GeralUtil.obterListaAtributosPorAnotacao(this, FXML.class);
            listaCampo.forEach((Object n) -> {
                if (n instanceof MaskedTextField) {
                    ((MaskedTextField) n).setPlainText("");
                } else if (n instanceof TextField) {
                    ((TextField) n).setText("");
                } else if (n instanceof DatePicker) {
                    ((DatePicker) n).getEditor().setText("");
                    ((DatePicker) n).setValue(null);
                } else if (n instanceof ComboBox) {
                    ((ComboBox) n).setValue(null);
                    ((ComboBox) n).getItems().clear();
                } else if (n instanceof ChoiceBox) {
                    ((ChoiceBox) n).setValue(null);
                    ((ChoiceBox) n).getItems().clear();
                } else if (n instanceof TextArea) {
                    ((TextArea) n).setText("");
                } else if (n instanceof RadioButton) {
                    ((RadioButton) n).setSelected(false);
                } else if (n instanceof CheckBox) {
                    ((CheckBox) n).setSelected(false);
                } else if (n instanceof TableView) {
                    ((TableView) n).getItems().clear();
                }
            });
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            LOG.error(cm.getMensagem("erro_limpar_campos"), ex);
        }
    }

    @Override
    public final Stage getStage() {
        return stage;
    }

    @Override
    public final void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public final void aoFecharJanela() {
        aoFecharJanelaEspecifico();
        limparParametros();
    }

    /**
     * Apaga todos os atributos da classe que estiverem marcados como parametros
     */
    private void limparParametros() {
        try {
            List<String> listaNomesParametros = GeralUtil.obterListaNomesAtributosPorAnotacao(this, Parametro.class);
            for (String nome : listaNomesParametros) {
                GeralUtil.setFieldInReflection(this, nome, null);
            }
        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOG.error(cm.getMensagem("erro_control_parametros_limpar", this.getClass().getName()), ex);
        }
    }

    @Override
    public boolean isNovaJanela() {
        return novaJanela;
    }

    @Override
    public void setNovaJanela(boolean novaJanela) {
        this.novaJanela = novaJanela;
    }

    public abstract void setFxmlFilePath(String filePath);

    public abstract void reinicializarJanelaEspecifico();

    public abstract void aoFecharJanelaEspecifico();
}
