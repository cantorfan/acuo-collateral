package com.acuo.collateral.modules.persistence;

import org.aopalliance.intercept.MethodInterceptor;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.services.Neo4jPersistService;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

public class Neo4jPersistModule extends PersistModule {

	private MethodInterceptor transactionInterceptor;

	@Override
	protected void configurePersistence() {

		bind(Neo4jPersistService.class).in(Singleton.class);
		bind(PersistService.class).to(Neo4jPersistService.class);
		bind(UnitOfWork.class).to(Neo4jPersistService.class);
		bind(Session.class).toProvider(Neo4jPersistService.class);

		transactionInterceptor = new Neo4jLocalTxnInterceptor();
		requestInjection(transactionInterceptor);
	}

	@Override
	protected MethodInterceptor getTransactionInterceptor() {
		return this.transactionInterceptor;
	}
}
