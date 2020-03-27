package com.taurus.javafx.ds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Diego
 * @param <L> Tipo generico que representa a Classe do objeto da linha do
 * TableView.
 * @param <B> Tipo generico do Bean que esta na lista
 */
public abstract class GenericDataSourceTableView<L, B> {

    private final ObservableList<L> data = FXCollections.observableArrayList();

    public GenericDataSourceTableView(B bean) {
        this.adicionar(bean);
    }

    public GenericDataSourceTableView(List<B> listaBean) {
        listaBean.forEach((bean) -> {
            this.adicionar(bean);
        });
    }

    protected abstract L criarLinhaView(B bean);

    /**
     * Retorna um bean do DataSource a partir da linha correspondente
     *
     * @param linhaView
     * @return
     */
    public abstract B getBean(L linhaView);

    public final ObservableList<L> getData() {
        return data;
    }

    /**
     * limpa o datasource
     */
    public final void limpar() {
        data.clear();
    }

    /**
     * Adiciona uma linha ao datasource
     *
     * @param bean
     */
    public final void adicionar(B bean) {
        L linhaView = criarLinhaView(bean);
        data.add(linhaView);
    }

    /**
     * Adiciona uma linha ao datasource no indice informado
     *
     * @param bean
     */
    public final void adicionar(int indice, B bean) {
        L linhaView = criarLinhaView(bean);
        data.add(indice, linhaView);
    }

    /**
     * Remove uma linha do dataSource
     *
     * @param linhaView
     * @return Bean removido
     */
    public final B remover(L linhaView) {
        data.remove(linhaView);
        return getBean(linhaView);
    }

    /**
     * Atualiza uma linha do dataSource
     *
     * @param linhaView linhaView de referencia que sera atualizada
     * @param bean Bean que sera incluido na linhaView indicada
     * @return nova linhaView
     */
    public final L atualizar(L linhaView, B bean) {
        L novaLinhaView = criarLinhaView(bean);
        data.set(data.indexOf(linhaView), novaLinhaView);
        return novaLinhaView;
    }

    /**
     * Retorna a lista de bean existente no datasource
     *
     * @return lista de bean
     */
    public final List<B> getListaBean() {
        List<B> beans = new ArrayList();
        this.data.forEach(linhaView -> {
            beans.add(getBean(linhaView));
        });
        return beans;
    }

    /**
     * Checa se existe um item na lista que corresponde ao Bean informado, se
     * existir retorna "L" senao retorna null
     *
     * @param bean
     * @return Objeto que representa a linha "L"
     */
    public final L getLinhaViewByBean(B bean) {
        Iterator<L> it = this.data.iterator();
        while (it.hasNext()) {
            L linhaView = it.next();
            if (bean.equals(getBean(linhaView))) {
                return linhaView;
            }
        }
        return null;
    }

    /**
     * Verifica se existe o bean no datasource
     *
     * @param bean
     * @return true se existir o bean, false se nao existir
     */
    public final boolean contem(B bean) {
        L linhaView = this.criarLinhaView(bean);
        return this.data.contains(linhaView);
    }

    /**
     * Atualiza as linhas do DataSource com os valores atuais dos Beans
     */
    public void recarregar() {
        this.data.forEach((linhaView) -> {
            this.atualizar(linhaView, this.getBean(linhaView));
        });
    }
}
