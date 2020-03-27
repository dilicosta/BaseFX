package com.taurus.javafx.componente;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

/**
 *
 * @author Diego
 * @param <T> Objeto que representa a linha da tabela
 */
public abstract class BotaoVariavelTableColumnFactory<T> implements Callback<TableColumn<T, Boolean>, TableCell<T, Boolean>> {

    public class DadosBotao {

        private String estiloBotao;
        private String mensagemTooltip;

        public DadosBotao(String estiloBotao, String mensagemTooltip) {
            this.estiloBotao = estiloBotao;
            this.mensagemTooltip = mensagemTooltip;
        }
    }

    public BotaoVariavelTableColumnFactory() {
    }

    @Override
    public TableCell<T, Boolean> call(TableColumn<T, Boolean> param) {

        return new TableCell<T, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                T linhaView = empty ? null : this.getTableView().getItems().get(this.getIndex());
                if (!empty && exibirItem(linhaView)) {
                    DadosBotao dadosBotao = BotaoVariavelTableColumnFactory.this.getDadosBotao(linhaView);
                    if (dadosBotao != null) {
                        setGraphic(this.criarBotao(dadosBotao));
                    } else {
                        setGraphic(null);
                    }
                } else {
                    setGraphic(null);
                }
            }

            private Button criarBotao(DadosBotao dadosBotao) {
                Button botao = new Button();
                Tooltip t = new Tooltip(dadosBotao.mensagemTooltip);
                botao.setTooltip(t);
                botao.getStyleClass().add(dadosBotao.estiloBotao);

                botao.setPrefSize(25, 25);
                botao.setOnAction((ActionEvent a) -> {
                    BotaoVariavelTableColumnFactory.this.eventoBotao(a, super.getTableView().getItems().get(this.getIndex()));
                });
                return botao;
            }
        };
    }

    public abstract DadosBotao getDadosBotao(T linhaView);

    public abstract void eventoBotao(ActionEvent actionEvent, T linhaView);

    protected abstract boolean exibirItem(T linhaView);
}
