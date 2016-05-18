package com.acuo.collateral.services;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;

import com.acuo.collateral.model.Entity;
import com.acuo.common.util.ArgChecker;
import com.google.inject.persist.Transactional;

public abstract class GenericService<T> implements Service<T> {

	public static final int DEPTH_LIST = 1;
	public static final int DEPTH_ENTITY = 1;

	@Inject
	protected Session session;

	@Transactional
	@Override
	public Iterable<T> findAll() {
		return session.loadAll(getEntityType(), DEPTH_LIST);
	}

	@Transactional
	@Override
	public T find(Long id) {
		return session.load(getEntityType(), id, DEPTH_ENTITY);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		session.delete(session.load(getEntityType(), id));
	}

	@Transactional
	@Override
	public T createOrUpdate(T entity) {
		ArgChecker.notNull(entity, "entity");
		session.save(entity, DEPTH_ENTITY);
		return find(((Entity) entity).getId());
	}

	public abstract Class<T> getEntityType();
}