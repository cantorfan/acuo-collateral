package com.acuo.collateral.services;

import com.acuo.collateral.model.Entity;
import com.acuo.common.util.ArgChecker;
import com.google.inject.persist.Transactional;
import org.neo4j.ogm.session.Session;

import javax.inject.Inject;
import javax.inject.Provider;

public abstract class GenericService<T> implements Service<T> {

    public static final int DEPTH_LIST = 1;
    public static final int DEPTH_ENTITY = 1;

    @Inject
    protected Provider<Session> sessionProvider;

    @Transactional
    @Override
    public Iterable<T> findAll() {
        return sessionProvider.get().loadAll(getEntityType(), DEPTH_LIST);
    }

    @Transactional
    @Override
    public T find(Long id) {
        return sessionProvider.get().load(getEntityType(), id, DEPTH_ENTITY);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Session session = sessionProvider.get();
        session.delete(session.load(getEntityType(), id));
    }

    @Transactional
    @Override
    public T createOrUpdate(T entity) {
        ArgChecker.notNull(entity, "entity");
        sessionProvider.get().save(entity, DEPTH_ENTITY);
        return find(((Entity) entity).getId());
    }

    public abstract Class<T> getEntityType();
}