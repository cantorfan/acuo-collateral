package com.acuo.collateral.services;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;

import com.acuo.collateral.model.Entity;
import com.acuo.common.util.ArgChecker;
import com.google.inject.persist.Transactional;

@Transactional
public abstract class GenericService<T> implements Service<T> {

	public static final int DEPTH_LIST = 0;
	public static final int DEPTH_ENTITY = 1;

	@Inject
	protected Session session;

	@Override
	public Iterable<T> findAll() {
		return session.loadAll(getEntityType(), DEPTH_LIST);
	}

	@Override
	public T find(Long id) {
		return session.load(getEntityType(), id, DEPTH_ENTITY);
	}

	@Override
	public void delete(Long id) {
		session.delete(session.load(getEntityType(), id));
	}

	@Override
	public T createOrUpdate(T entity) {
		ArgChecker.notNull(entity, "entity");
		session.save(entity, DEPTH_ENTITY);
		return find(((Entity) entity).getId());
	}

	public abstract Class<T> getEntityType();
}