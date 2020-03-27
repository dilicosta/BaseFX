package com.taurus.javafx.ds;

import javafx.scene.control.TreeItem;

/**
 *
 * @author Diego
 * @param <L> Tipo Generico que representa a linha do TreeItem
 * @param <B> Tipo generico que representa o Bean
 */
public abstract class GenericDataSourceTreeTableView<L, B> {

    private final TreeItem<L> rootNode;

    public GenericDataSourceTreeTableView() {
        this.rootNode = new TreeItem<>();
    }

    protected abstract L getLinhaView(B bean);
    
    public abstract B getBean(L linhaView);

    public final TreeItem<L> getRoot() {
        return this.rootNode;
    }

    /**
     * limpa o datasource
     */
    public final void limpar() {
        this.rootNode.getChildren().clear();
    }

    /**
     * Adiciona uma linha ao datasource
     *
     * @param bean
     * @param treeItemPai
     * @return
     */
    public final TreeItem<L> adicionar(B bean, TreeItem<L> treeItemPai) {
        if (treeItemPai == null) {
            treeItemPai = this.rootNode;
        }
        L linhaView = getLinhaView(bean);
        TreeItem<L> treeItem = new TreeItem(linhaView);
        treeItemPai.getChildren().add(treeItem);
        return treeItem;
    }

    /**
     * Remove uma linha do dataSource
     *
     * @param treeItem
     * @return
     */
    public final B remover(TreeItem<L> treeItem) {
        L linhaView = treeItem.getValue();
        treeItem.getParent().getChildren().remove(treeItem);
        return getBean(linhaView);
    }

    /**
     * Atualiza o bean do TreeItem informado
     *
     * @param bean
     * @param treeItem
     */
    public void atualizar(B bean, TreeItem<L> treeItem) {
        L linhaViewNovo = this.getLinhaView(bean);
        treeItem.setValue(linhaViewNovo);
    }

    /**
     * Retorna um bean do DataSource a partir da linha correspondente
     *
     * @param treeItem
     * @return
     */
    public final B getBean(TreeItem<L> treeItem) {
        return getBean(treeItem.getValue());
    }
}
