package com.taurus.dao;

import com.taurus.dao.ParametroPesquisa.Operador;
import com.taurus.entidade.BaseEntity;
import com.taurus.exception.CampoPesquisaException;
import com.taurus.exception.PersistenciaException;
import com.taurus.util.ControleMensagem;
import com.taurus.util.GeralUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe generica para realizar as operacoes de consultas e persistencias no
 * banco de dados
 *
 * @author Diego - TJMG
 * @param <T> Tipo generico de entidade
 */
@Repository("genericDAO") //Informa ao Spring que esta classe sera um bean mapeado do tipo que realiza persisencia
public abstract class GenericDAO<T extends BaseEntity> implements IGenericDAO<T> {

    private static final Log LOG = LogFactory.getLog(GenericDAO.class);

    protected ControleMensagem controleMensagem = ControleMensagem.getInstance();

    // Informa ao Spring para fazer a injecao automatica do EntityManager
    // PersistenceContextType.EXTENDED extende o contexto de persistencia a todos metodos anotados como @Transaction
    @PersistenceContext(unitName = "PU")
    private EntityManager entityManager;

    private final Class<T> tipoClasse;

    public GenericDAO(Class<T> tipoClasse) {
        this.tipoClasse = tipoClasse;
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * Insere uma nova entidade no Banco de Dados e a deixa em estado de
     * transiente
     *
     * @param entity Objeto que sera incluido
     * @throws PersistenciaException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void persist(T entity) throws PersistenciaException {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_salvar", entity.getClass().getName()), e);
        }
    }

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
    @Override
    public T merge(T entity) throws PersistenciaException {
        try {
            T entityUp = entityManager.merge(entity);
            return entityUp;
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_atualizar", entity.getClass().getName()), e);
        }
    }

    /**
     * Exclui o registro correspondente ao da entidade informada
     *
     * @param entity Objeto que sera excluido no banco de dados
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void excluir(T entity) throws PersistenciaException {
        if (entity.getId() == null) {
            return;
        }
        try {
            entity = this.atacharEntidade(entity);
            entityManager.remove(entity);
            LOG.debug(controleMensagem.getMensagem("op_exclusao_registro", entity.getClass().getName(), entity.getId().toString()));
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_excluir", entity.getClass().getSimpleName()), e);
        }
    }

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
    @Override
    public List<T> pesquisarPorAtributos(T entity) throws CampoPesquisaException, PersistenciaException {
        return this.pesquisarPorAtributos(entity, true);
    }

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
    @Override
    public List<T> pesquisarPorAtributos(T entity, boolean ordemCrescente, String... atributosOrdenacao) throws CampoPesquisaException, PersistenciaException {
        if (isAtributosVazios(entity)) {
            throw new CampoPesquisaException(controleMensagem.getMensagem("erro_pesquisa_entity_vazio"));
        }
        try {
            Session sessao = (Session) entityManager.getDelegate();
            Criteria criteria = sessao.createCriteria(entity.getClass());
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            Example example = Example.create(entity).enableLike(MatchMode.ANYWHERE);
            criteria.add(example);
            for (String nomeAtributo : atributosOrdenacao) {
                if (ordemCrescente) {
                    criteria.addOrder(Order.asc(nomeAtributo));
                } else {
                    criteria.addOrder(Order.desc(nomeAtributo));
                }
            }
            return criteria.list();
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_pesquisar", entity.getClass().getName()), e);
        }

    }

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
    @Override
    public List<T> pesquisarPorParametros(List<ParametroPesquisa> listaParametroPesquisas, boolean ordemCrescente, String... atributosOrdenacao) throws PersistenciaException {
        /*
        if (CollectionUtils.isEmpty(listaParametroPesquisas)) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_pesquisa_parametros_vazio"));
        }
         */
        try {
            return this.getCriteriaPesquisarPorParametros(listaParametroPesquisas, ordemCrescente, atributosOrdenacao).list();
        } catch (HibernateException e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_pesquisar", this.tipoClasse.getName()), e);
        }
    }

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
    @Override
    public Long pesquisarTotalRegistrosPesquisaPorParametros(List<ParametroPesquisa> listaParametroPesquisas) throws PersistenciaException {
        /*
        if (listaParametroPesquisas == null || listaParametroPesquisas.isEmpty()) {
            throw new CampoPesquisaException(controleMensagem.getMensagem("erro_pesquisa_entity_vazio"));
        }
         */
        try {
            Criteria criteria = this.getCriteriaPesquisarPorParametros(listaParametroPesquisas, true);
            return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_pesquisar", this.tipoClasse.getName()), e);
        }
    }

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
    @Override
    public List<T> pesquisarPorParametrosPaginada(List<ParametroPesquisa> listaParametroPesquisas, int pagina, int registrosPorPagina, boolean ordemCrescente, String... atributosOrdenacao) throws PersistenciaException {
        /*
        if (listaParametroPesquisas == null || listaParametroPesquisas.isEmpty()) {
            throw new CampoPesquisaException(controleMensagem.getMensagem("erro_pesquisa_entity_vazio"));
        }
         */
        try {
            Criteria criteria = this.getCriteriaPesquisarPorParametros(listaParametroPesquisas, ordemCrescente, atributosOrdenacao);
            criteria.setFirstResult((pagina - 1) * registrosPorPagina);
            criteria.setMaxResults(registrosPorPagina);
            return criteria.list();
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_pesquisar", this.tipoClasse.getName()), e);
        }
    }

    /**
     * Retorna uma lista com todas as entidades da entidade generica referente
     * da classe
     *
     * @return Lista de entidades
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<T> pesquisarTodos() throws PersistenciaException {
        return this.pesquisarTodos(true);
    }

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
    @Override
    public List<T> pesquisarTodos(boolean ordemCrescente, String... atributosOrdenacao) throws PersistenciaException {
        try {
            Session sessao = (Session) entityManager.getDelegate();
            Criteria criteria = sessao.createCriteria(this.tipoClasse);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            for (String nomeAtributo : atributosOrdenacao) {
                if (ordemCrescente) {
                    criteria.addOrder(Order.asc(nomeAtributo));
                } else {
                    criteria.addOrder(Order.desc(nomeAtributo));
                }
            }
            return criteria.list();
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_pesquisar", this.tipoClasse.getName()), e);
        }

    }

    /**
     * Pesquisa um registro de uma entidade pelo seu id
     *
     * @param id Valor do ID
     * @return Entidade
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public T pesquisarPorId(Object id) throws PersistenciaException {
        try {
            Session sessao = (Session) entityManager.getDelegate();
            Criteria criteria = sessao.createCriteria(this.tipoClasse);
            criteria.add(Restrictions.eq("id", id));
            return (T) criteria.uniqueResult();
        } catch (Exception e) {
            throw new PersistenciaException(controleMensagem.getMensagem("erro_persist_pesquisar", tipoClasse.getName()), e);
        }
    }

    /**
     * Verifica se os atributos que representam colunas da entidade est�o vazias
     *
     * @param objeto
     * @return true se todos atributos estiverem vazios, false caso contrario
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    private boolean isAtributosVazios(T entity) throws PersistenciaException {
        Class entityClass = entity.getClass();
        do {
            Field[] arrayAtributos = entityClass.getDeclaredFields();
            for (Field atributo : arrayAtributos) {
                try {
                    // Se o atributo tiver a notacao "Column"
                    if (atributo.isAnnotationPresent(Column.class) || atributo.isAnnotationPresent(ManyToOne.class)) {
                        try {
                            // Se o atributo possui valor
                            if (GeralUtil.getFieldInReflection(entity, atributo.getName()) != null) {
                                return false;
                            }
                        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
                            throw new PersistenciaException(controleMensagem.getMensagem("erro_leitura_atributo", atributo.getName(), entity.getClass().getName()), e);
                        }
                    }
                } catch (NullPointerException e) {
                    // O atributo nao tem nenhuma notacao, desconsidera-se
                }
            }
            // Passa a considerar os atributos da superclasse no proximo ciclo
            entityClass = entityClass.getSuperclass();
        } while (entityClass != null);
        return true;
    }

    /**
     * Faz com que a entidade se torne transiente dentro do contexto de
     * persistencia
     *
     * @param entity Entidade que se tornara transiente
     * @return Entidade transiente
     * @throws com.taurus.exception.PersistenciaException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public T atacharEntidade(T entity) throws PersistenciaException {
        // entidade ja eh transiente
        if (this.isTransiente(entity)) {
            return entity;
        } else {
            return this.pesquisarPorId(entity.getId());
        }
    }

    /**
     * Verifica se um entidade eh transiente ou detached
     *
     * @param entity Entidade
     * @return true se for transiente, false se for detached
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isTransiente(T entity) {
        return entityManager.contains(entity);
    }

    private Criteria getCriteriaPesquisarPorParametros(List<ParametroPesquisa> listaParametroPesquisas, boolean ordemCrescente, String... atributosOrdenacao) {
        Session sessao = (Session) entityManager.getDelegate();
        Criteria criteria = sessao.createCriteria(this.tipoClasse);

        // Para não exibir uma lista duplicada quando houver join 1xn
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<String> joinsRealizados = new ArrayList();
        listaParametroPesquisas.forEach((parametroPesquisa) -> {
            String nivelAtributo[] = parametroPesquisa.getNomeAtributo().split("\\.");
            String nomeAtributo = null;
            if (nivelAtributo.length == 1) {
                nomeAtributo = nivelAtributo[0];
            } else {
                int t = nivelAtributo.length;
                nomeAtributo = nivelAtributo[t - 2] + "_." + nivelAtributo[t - 1];
                String alias = null;
                for (int i = 0; i < t - 1; i++) {
                    if (!joinsRealizados.contains(nivelAtributo[i])) {
                        criteria.createAlias(alias == null ? "" + nivelAtributo[i] : alias + "." + nivelAtributo[i], nivelAtributo[i] + "_", JoinType.INNER_JOIN);
                        joinsRealizados.add(nivelAtributo[i]);
                    }
                    alias = nivelAtributo[i] + "_";
                }

            }
            if (parametroPesquisa.getValorAtributo() == null) {
                Operador operador = parametroPesquisa.getOperador();
                switch (operador) {
                    case IGUAL:
                        criteria.add(Restrictions.isNull(nomeAtributo));
                        break;
                    case DIFERENTE:
                        criteria.add(Restrictions.isNotNull(nomeAtributo));
                        break;
                }
            } else {
                Operador operador = parametroPesquisa.getOperador();
                if (null == operador) {
                    criteria.add(Restrictions.eq(nomeAtributo, parametroPesquisa.getValorAtributo()));
                } else {
                    switch (operador) {
                        case IGUAL:
                            criteria.add(Restrictions.eq(nomeAtributo, parametroPesquisa.getValorAtributo()));
                            break;
                        case DIFERENTE:
                            criteria.add(Restrictions.ne(nomeAtributo, parametroPesquisa.getValorAtributo()));
                            break;
                        case LIKE:
                            criteria.add(Restrictions.ilike(nomeAtributo, "%" + parametroPesquisa.getValorAtributo() + "%"));
                            break;
                        case MAIOR:
                            criteria.add(Restrictions.gt(nomeAtributo, parametroPesquisa.getValorAtributo()));
                            break;
                        case MAIOR_IGUAL:
                            criteria.add(Restrictions.ge(nomeAtributo, parametroPesquisa.getValorAtributo()));
                            break;
                        case MENOR:
                            criteria.add(Restrictions.lt(nomeAtributo, parametroPesquisa.getValorAtributo()));
                            break;
                        case MENOR_IGUAL:
                            criteria.add(Restrictions.le(nomeAtributo, parametroPesquisa.getValorAtributo()));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        if (atributosOrdenacao != null) {
            for (String nomeAtributo : atributosOrdenacao) {

                String nivelAtributo[] = nomeAtributo.split("\\.");
                String nomeOrdenacao;
                if (nivelAtributo.length == 1) {
                    nomeOrdenacao = nivelAtributo[0];
                } else {
                    int t = nivelAtributo.length;
                    nomeOrdenacao = nivelAtributo[t - 2] + "_." + nivelAtributo[t - 1];

                    String alias = null;
                    for (int i = 0; i < t - 1; i++) {
                        if (!joinsRealizados.contains(nivelAtributo[i])) {
                            criteria.createAlias(alias == null ? "" + nivelAtributo[i] : alias + "." + nivelAtributo[i], nivelAtributo[i] + "_", JoinType.INNER_JOIN);
                            joinsRealizados.add(nivelAtributo[i]);
                        }
                        alias = nivelAtributo[i] + "_";
                    }
                }

                if (!GenericValidator.isBlankOrNull(nomeOrdenacao)) {
                    if (ordemCrescente) {
                        criteria.addOrder(Order.asc(nomeOrdenacao));
                    } else {
                        criteria.addOrder(Order.desc(nomeOrdenacao));
                    }
                }
            }
        }
        return criteria;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void sincronizar() throws PersistenciaException {
        try {
            this.entityManager.flush();
        } catch (Exception ex) {
            throw new PersistenciaException(ex);
        }
    }
}
