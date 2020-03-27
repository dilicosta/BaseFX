package com.taurus.entidade;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Classe base das entidades a serem mapeadas pelo sistema. Todas as entidades
 * devem extender esta classe.
 *
 * @author Diego Lima
 * @param <ID> Tipo do atributo id da entidade
 */
@MappedSuperclass
public abstract class BaseEntityID<ID extends Serializable> extends BaseEntity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
}
