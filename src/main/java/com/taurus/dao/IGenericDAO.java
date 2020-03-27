package com.taurus.dao;

import com.taurus.entidade.BaseEntity;
import com.taurus.exception.CampoPesquisaException;
import com.taurus.exception.PersistenciaException;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Diego
 * @param <T>
 */
public interface IGenericDAO<T extends BaseEntity> {

    /**
     * Faz com que a entidade se torne transiente dentro do contexto de
     * persistencia
     *
     * @param entity Entidade que se tornara transiente
     * @return Entidade transiente
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    T atacharEntidade(T entity) throws PersistenciaException;

    /**
     * Exclui o registro correspondente ao da entidade informada
     *
     * @param entity Objeto que sera excluido no banco de dados
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void excluir(T entity) throws PersistenciaException;

    /**
     * Verifica se um entidade eh transiente ou detached
     *
     * @param entity Entidade
     * @return true se for transiente, false se for detached
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    boolean isTransiente(T entity);

    /**
     * Retorna uma entidade em estado gerenciavel e persiste os dados da
     * entidade passada para o contexto
     *
     * @param entity Objeto que sera atualizado
     * @return Retorna a entidade transiente
     * @throws com.taurus.exception.PersistenciaException
     *
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    T merge(T entity) throws PersistenciaException;

    /**
     * Insere uma nova entidade no Banco de Dados e a deixa em estado de
     * transiente
     *
     * @param entity Objeto que sera incluido
     * @return Retorna a entidade com sua ID
     * @throws PersistenciaException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void persist(T entity) throws PersistenciaException;

    /**
     * Retorna uma lista de entidades correspondente a entidade informada,
     * filtrando os registros pelos atributos correspondentes as colunas, que
     * possuam valores na entidade informada
     *
     * @param entity Entidade que se deseja pesquisar
     * @return Lista de entidades com o resultado da pesquisa
     * @throws CampoPesquisaException Caso nenhum atributo correspondente as
     * colunas da tabela possua valor, esta excecao sera lancada. Este
     * procedimento evita uma consulta acidental de full scan de todos os
     * registros da tabela.
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    List<T> pesquisarPorAtributos(T entity) throws CampoPesquisaException, PersistenciaException;

    /**
     * Retorna uma lista ordenada de entidades correspondente a entidade
     * informada, filtrando os registros pelos atributos correspondentes as
     * colunas, que possuam valores na entidade informada
     *
     * @param entity Entidade que se deseja pesquisar
     * @param ordemCrescente true se deseja a lista em ordem crescente e false
     * para descrescente
     * @param atributosOrdenacao nomes dos atributos da Entidade que serao
     * utilizados na ordenacao
     * @return Lista de entidades com o resultado da pesquisa
     * @throws CampoPesquisaException Caso nenhum atributo correspondente as
     * colunas da tabela possua valor, esta excecao sera lancada. Este
     * procedimento evita uma consulta acidental de full scan de todos os
     * registros da tabela.
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    List<T> pesquisarPorAtributos(T entity, boolean ordemCrescente, String... atributosOrdenacao) throws CampoPesquisaException, PersistenciaException;

    /**
     * Pesquisa um registro de uma entidade pelo seu id
     *
     * @param id Valor do ID
     * @return Entidade
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    T pesquisarPorId(Object id) throws PersistenciaException;

    /**
     * Retorna uma lista ordenada de entidades correspondente a entidade
     * informada, filtrando os registros pelos campos informados
     *
     * @param listaParametroPesquisas lista com os nomes, valores e operadores
     * que os atributos devem possuir para as condicoes da pesquisa
     * @param ordemCrescente true se deseja a lista em ordem crescente e false
     * para descrescente
     * @param atributosOrdenacao nomes dos atributos da Entidade que serao
     * utilizados na ordenacao
     * @return Lista de entidades com o resultado da pesquisa
     * @throws PersistenciaException Caso ocorra algum erro de BD ou nao seja
     * informado os parametros
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    List<T> pesquisarPorParametros(List<ParametroPesquisa> listaParametroPesquisas, boolean ordemCrescente, String... atributosOrdenacao) throws PersistenciaException;

    /**
     * Retorna uma lista paginada e ordenada de entidades correspondente a
     * entidade informada, filtrando os registros pelos campos informados
     *
     * @param listaParametroPesquisas lista com os nomes, valores e operadores
     * que os atributos devem possuir para as condicoes da pesquisa
     * @param pagina Numero corresponde a pagina de pesquisa
     * @param registrosPorPagina numero total de registros que devem ser
     * apresentados na pagina
     * @param ordemCrescente true se deseja a lista em ordem crescente e false
     * para descrescente
     * @param atributosOrdenacao nomes dos atributos da Entidade que serao
     * utilizados na ordenacao
     * @return Lista de entidades com o resultado da pesquisa
     * @throws PersistenciaException Caso ocorra algum erro de BD
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    List<T> pesquisarPorParametrosPaginada(List<ParametroPesquisa> listaParametroPesquisas, int pagina, int registrosPorPagina, boolean ordemCrescente, String... atributosOrdenacao) throws PersistenciaException;

    /**
     * Retorna uma lista com todas as entidades da entidade generica referente
     * da classe
     *
     * @return Lista de entidades
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    List<T> pesquisarTodos() throws PersistenciaException;

    /**
     * Retorna uma lista com todas as entidades da entidade generica referente
     * da classe, ordenada pelos atributos informados.
     *
     * @param ordemCrescente true se deseja a lista em ordem crescente e false
     * para descrescente
     * @param atributosOrdenacao nomes dos atributos da Entidade que serao
     * utilizados na ordenacao
     * @return Lista de entidades com o resultado da pesquisa
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    List<T> pesquisarTodos(boolean ordemCrescente, String... atributosOrdenacao) throws PersistenciaException;

    /**
     * Retorna a quantidade de registros existentes da entidade correspondente,
     * utilizando os filtros informados
     *
     * @param listaParametroPesquisas lista com os nomes, valores e operadores
     * dos atributos de pesquisa
     * @return numero total de registros encontrados
     * @throws PersistenciaException Caso ocorra algum erro de BD
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    Long pesquisarTotalRegistrosPesquisaPorParametros(List<ParametroPesquisa> listaParametroPesquisas) throws PersistenciaException;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void sincronizar() throws PersistenciaException;

}
