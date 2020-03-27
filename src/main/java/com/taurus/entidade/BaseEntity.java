package com.taurus.entidade;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

/**
 * Classe base das entidades a serem mapeadas pelo sistema. Todas as entidades
 * devem extender esta classe.
 *
 * @author Diego Lima
 * @param <ID> Tipo do atributo id da entidade
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

    public abstract ID getId();

    public abstract void setId(ID id);

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();
}
