package com.taurus.javafx.util;

import com.taurus.javafx.controller.FXMLBaseController;
import com.taurus.javafx.controller.IFXMLBaseController;
import com.taurus.javafx.controller.IFXMLMultiCenasBaseController;
import com.taurus.javafx.controller.PopupBaseController;
import com.taurus.util.ContextUtil;
import com.taurus.util.ControleMensagem;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

/**
 * Classe util para abrir as janelas da aplicação
 *
 * @author Diego Lima
 * @param <B> Os controles que devem extender FXMLController
 * @param <P> Os controles que devem extender PopupBaseController
 */
public class ControleJanela<B extends FXMLBaseController, P extends PopupBaseController> {

    private static final Log LOG = LogFactory.getLog(ControleJanela.class);

    private ControleMensagem cm = ControleMensagem.getInstance();
    private static ControleJanela instancia;

    private enum EstiloJanela {
        POPUP, COM_REDIMENSIONAMENTO, SEM_REDIMENSIONAMENTO;
    }

    private ControleJanela() {
        super();
        listaStages = new HashMap<>();
    }

    public static ControleJanela getInstance() {
        if (instancia == null) {
            instancia = new ControleJanela();
        }
        return instancia;
    }

    public enum PosicaoReferenciaPopup {
        DIREITA_ACIMA, DIREITA_CENTRO, DIREITA_ABAIXO,
        ESQUERDA_ACIMA, ESQUERDA_CENTRO, ESQUERDA_ABAIXO,
        CENTRO_ACIMA, CENTRO_CENTRO, CENTRO_ABAIXO;
    }
    private final Map<Integer, Stage> listaStages;

    public void setPrimaryStage(Stage primaryStage) {
        listaStages.put(0, primaryStage);
    }

    /**
     * Abre uma janela
     *
     * @param windowStageId Enun que identifica a stage da janela que sera
     * aberta
     * @param ownerStageId Enum que identifica a stage pai da janela
     * @param controllerClass classe de controle da janela que sera aberta
     * @param chaveTitulo titulo da janela que sera aberta ou chave de
     * identificacao do mesmo
     * @param novaJanela True se deve abrir uma janela limpa, False se for abrir
     * uma janela com os dados anteriores
     */
    public void abrirJanela(Integer windowStageId, Integer ownerStageId, Class<B> controllerClass, String chaveTitulo, boolean novaJanela) {
        this.abrirJanela(windowStageId, ownerStageId, controllerClass, chaveTitulo, EstiloJanela.SEM_REDIMENSIONAMENTO, novaJanela);
    }

    public void abrirJanelaComRedimensionamento(Integer windowStageId, Integer ownerStageId, Class<B> controllerClass, String chaveTitulo, boolean novaJanela) {
        this.abrirJanela(windowStageId, ownerStageId, controllerClass, chaveTitulo, EstiloJanela.COM_REDIMENSIONAMENTO, novaJanela);
    }

    private void abrirJanela(Integer windowStageId, Integer ownerStageId, Class<B> controllerClass, String chaveTitulo, EstiloJanela estiloJanela, boolean novaJanela) {
        String titulo = cm.getMensagem(chaveTitulo);
        Stage stage = listaStages.get(windowStageId);
        if (stage == null || !stage.isShowing()) {
            IFXMLBaseController controller = ContextUtil.getBean(controllerClass);
            stage = this.prepararJanela(stage, listaStages.get(ownerStageId), controller, titulo, estiloJanela, novaJanela);
            listaStages.put(windowStageId, stage);
            stage.centerOnScreen();
            stage.showAndWait();
        }
    }

    /**
     * Abre uma janela no estilo Popup
     *
     * @param windowStageId enum do Palco da janela que sera aberta
     * @param ownerStageId enum do Palco pai
     * @param controllerClass Tipo da classe de controle da janela
     * @param referencia Objeto de referencia da janela onde o Popup sera aberto
     * @param posRef Posicao sobre a referencia de como o Popup sera aberto
     */
    public void abrirJanelaPopup(Integer windowStageId, Integer ownerStageId, Class<B> controllerClass, Region referencia, PosicaoReferenciaPopup posRef) {
        Stage stage = listaStages.get(windowStageId);
        IFXMLBaseController controller = ContextUtil.getBean(controllerClass);
        stage = this.prepararJanela(stage, listaStages.get(ownerStageId), controller, null, EstiloJanela.POPUP, true);
        listaStages.put(windowStageId, stage);

        Region nodePopup = (Region) controller.getView();
        Point2D posicao = obterPosicaoReferenciaPopup(stage, referencia, posRef, nodePopup);

        stage.setX(posicao.getX());
        stage.setY(posicao.getY());
        stage.showAndWait();
    }

    /**
     * Abre um Popup
     *
     * @param ownerStageId enum do Palco pai do Popup
     * @param controllerClass Classe de controle do Popup
     * @param referencia Objeto de referencia da janela onde o Popup sera aberto
     * @param posRef Posicao sobre a referencia de como o Popup sera aberto
     */
    public void abrirPopup(Integer ownerStageId, Class<P> controllerClass, Region referencia, PosicaoReferenciaPopup posRef) {
        Stage ownerStage = listaStages.get(ownerStageId);
        IFXMLBaseController controller = ContextUtil.getBean(controllerClass);
        controller.reinicializarJanelaBase();
        controller.aoAbrirJanela();

        Region nodePopup = (Region) controller.getView();
        Point2D posicao = this.obterPosicaoReferenciaPopup(ownerStage, referencia, posRef, nodePopup);
        Popup popup = new Popup();
        popup.getContent().add(nodePopup);
        popup.setAutoHide(true);
        popup.show(ownerStage, posicao.getX(), posicao.getY());
    }

    /**
     * Abre a proxima cena na janela atual
     *
     * @param controller Classe de controle da janela multi cenas
     */
    public void abrirProximaCena(IFXMLMultiCenasBaseController controller) {
        if (controller.getIndiceCena() + 1 < controller.getListaView().length) {
            this.abrirCena(controller, controller.getIndiceCena() + 1);
        }
    }

    /**
     * Abra a cena anterior da janela atual
     *
     * @param controller Classe de controle da janela multi cenas
     */
    public void abrirCenaAnterior(IFXMLMultiCenasBaseController controller) {
        if (controller.getIndiceCena() > 0) {
            this.abrirCena(controller, controller.getIndiceCena() - 1);
        }
    }

    /**
     * Abre a cena indicada pelo indice da janela atual
     *
     * @param controller Classe de controle das janelas multi cenas
     * @param indice indice da cena
     */
    public void abrirCena(IFXMLMultiCenasBaseController controller, int indice) {
        Region page = (Region) controller.getListaView()[indice];
        if (page.getScene() == null) {
            Scene scene = new Scene(page);
            scene.getStylesheets().add(ControleJanela.class.getResource("/css/theme_base.css").toExternalForm());
            scene.getStylesheets().add(ControleJanela.class.getResource("/css/theme.css").toExternalForm());
            controller.getStage().setScene(scene);
        } else {
            controller.getStage().setScene(page.getScene());
        }
        controller.setIndiceCena(indice);
        controller.aoAbrirCena(indice);
        controller.getStage().centerOnScreen();
        controller.getStage().show();
    }

    /**
     * Prepara uma janela para ser exibida
     *
     * @param ownerStage Janela Pai
     * @param controllerClass classe de controle da janela que sera aberta
     * @param windowStage janela que sera aberta
     * @param tituloJanela titulo da janela que sera aberta
     * @param novaJanela True se deve abrir uma janela limpa, False se for abrir
     * uma janela com os dados anteriores
     * @return Stage da janela aberta
     */
    private Stage prepararJanela(Stage windowStage, Stage ownerStage, IFXMLBaseController controller, String tituloJanela, EstiloJanela estiloJanela, boolean novaJanela) {

        boolean novoPalco = false;
        if (windowStage == null || windowStage.getOwner() != ownerStage) {
            Stage stageTmp = new Stage();
            novoPalco = true;

            EventHandler eventoFecharJanela = (EventHandler) (Event event) -> {
                event.consume();
                controller.aoFecharJanela();
                stageTmp.close();
            };

            windowStage = stageTmp;
            this.inicializarStage(windowStage, tituloJanela, eventoFecharJanela, estiloJanela);
            //informa quem eh o stage pai
            windowStage.initOwner(ownerStage);
        }

        Region page = (Region) controller.getView();

        if (novaJanela && page.getScene() != null) {
            controller.reinicializarJanelaBase();
        }
        controller.setNovaJanela(novaJanela);

        if (page.getScene() == null) {
            Scene scene = new Scene(page);
            scene.getStylesheets().add(ControleJanela.class.getResource("/css/theme_base.css").toExternalForm());
            scene.getStylesheets().add(ControleJanela.class.getResource("/css/theme.css").toExternalForm());
            windowStage.setScene(scene);
        } // Cena ja existe em outro palco 
        else if (novoPalco) {
            windowStage.setScene(controller.getStage().getScene());
        }

        controller.setStage(windowStage);
        controller.aoAbrirJanela();
        if (controller instanceof IFXMLMultiCenasBaseController) {
            ((IFXMLMultiCenasBaseController) controller).aoAbrirCena(0);
        }

        return windowStage;
    }

    /**
     * Inicializa uma janela para ser aberta
     *
     * @param stage Janela
     * @param titulo Titulo da janela
     * @param eventoFecharJanela evento que sera executado ao fechar a janela
     */
    private void inicializarStage(Stage stage, String titulo, EventHandler eventoFecharJanela, EstiloJanela estiloJanela) {

        if (estiloJanela == EstiloJanela.POPUP) {
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
        } else {
            stage.setTitle(titulo);
            stage.initModality(Modality.WINDOW_MODAL);

            // Remove os botoes de maximizar e minimizar
            stage.setResizable(estiloJanela == EstiloJanela.COM_REDIMENSIONAMENTO);
        }

        if (eventoFecharJanela != null) {
            stage.setOnCloseRequest(eventoFecharJanela);
        }
    }

    public void exibirAviso(String mensagem) {
        exibirDialog("Aviso", mensagem, AlertType.WARNING);
    }

    public void exibirAviso(String mensagem, String detalhes) {
        exibirDialog("Aviso", mensagem, detalhes, AlertType.WARNING);
    }

    public void exibirInformacao(String mensagem) {
        exibirDialog("Informação", mensagem, AlertType.INFORMATION);
    }

    public void exibirErro(String mensagem) {
        exibirDialog("Erro", mensagem, AlertType.ERROR);
    }

    public void exibirErro(String mensagem, String detalhes) {
        exibirDialog("Erro", mensagem, detalhes, AlertType.ERROR);
    }

    private void exibirDialog(String titulo, String mensagem, AlertType tipo) {
        exibirDialog(titulo, mensagem, null, tipo);
    }

    private void exibirDialog(String titulo, String mensagem, String detalhes, AlertType tipo) {
        titulo = cm.getMensagem(titulo);
        mensagem = cm.getMensagem(mensagem);
        Alert alert = new Alert(tipo);
        alert.setHeaderText(mensagem);
        alert.setTitle(titulo);
        if (detalhes != null) {
            alert.setContentText(detalhes);
        }
        alert.showAndWait();
    }

    public boolean exibirDialogSimNao(String titulo, String mensagem, String detalhes, String... parametrosMsg) {
        titulo = cm.getMensagem(titulo);
        mensagem = cm.getMensagem(mensagem, parametrosMsg);
        Alert dialogo = new Alert(Alert.AlertType.CONFIRMATION);
        ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
        dialogo.setTitle(titulo);
        dialogo.setHeaderText(mensagem);
        if (!GenericValidator.isBlankOrNull(detalhes)) {
            dialogo.setContentText(cm.getMensagem(detalhes));
        }
        dialogo.getButtonTypes().setAll(btnSim, btnNao);
        Optional opt = dialogo.showAndWait();
        return ((ButtonType) opt.get()).getText().equals("Sim");
    }

    public Optional<String> exibirDialogoTexto(String titulo, String mensagemCabecalho, String mensagemTexto) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(titulo);
        dialog.setHeaderText(mensagemCabecalho);
        dialog.setContentText(mensagemTexto);

        return dialog.showAndWait();
    }

    private Point2D obterPosicaoReferenciaPopup(Stage ownerStage, Region referencia, PosicaoReferenciaPopup posRef, Region janelaPopup) {
        Point2D posicao;
        double refX = 0, refY = 0;

        // Referencia X
        switch (posRef) {

            case ESQUERDA_ACIMA:
            case ESQUERDA_CENTRO:
            case ESQUERDA_ABAIXO:
                refX = -janelaPopup.getWidth() - 2;
                break;
            case CENTRO_ACIMA:
            case CENTRO_CENTRO:
            case CENTRO_ABAIXO:
                refX = 0;
                break;
            case DIREITA_ACIMA:
            case DIREITA_CENTRO:
            case DIREITA_ABAIXO:
                refX = referencia.getWidth() + 2;
        }

        // Referencia Y
        switch (posRef) {
            case ESQUERDA_ACIMA:
            case CENTRO_ACIMA:
            case DIREITA_ACIMA:
                refY = -janelaPopup.getHeight() - 5;
                break;
            case ESQUERDA_CENTRO:
            case CENTRO_CENTRO:
            case DIREITA_CENTRO:
                refY = 0;
                break;
            case ESQUERDA_ABAIXO:
            case CENTRO_ABAIXO:
            case DIREITA_ABAIXO:
                refY = referencia.getHeight() + 5;
                break;
        }

         posicao = referencia.localToScreen(refX, refY);
         
//        if (posicao.getX() < 0) {
//            posicao = posicao.add(refX, refY);
//        } else if (refX > ownerStage.getWidth()) {
//            refX = ownerStage.getWidth() - janelaPopup.getWidth();
//        }
//
//        if (refY < 0) {
//            refY = 0;
//        } else if (refY > ownerStage.getHeight()) {
//            refY = ownerStage.getHeight() - janelaPopup.getHeight();
//        }

       
        return posicao;
    }
}
