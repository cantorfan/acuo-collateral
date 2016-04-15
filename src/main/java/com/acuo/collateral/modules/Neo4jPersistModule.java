package com.acuo.collateral.modules;

import java.util.Properties;

import org.aopalliance.intercept.MethodInterceptor;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.services.Neo4jPersistService;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

/**
 * Created by markangrish on 11/04/2016.
 */
public class Neo4jPersistModule extends PersistModule {
	private final String[] packages;

	private MethodInterceptor transactionInterceptor;

	public Neo4jPersistModule(String... packages) {
		this.packages = packages;
	}

	@Override
	protected void configurePersistence() {

		bindNeo4jAnnotation();

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

	private void bindNeo4jAnnotation() {

		bind(String[].class).annotatedWith(Neo4j.class).toInstance(packages);

		Properties properties = new Properties();
		properties.put("neo4j.ogm.driver", System.getProperty("neo4j.ogm.driver"));
		properties.put("neo4j.ogm.url", System.getProperty("neo4j.ogm.url"));
		properties.put("neo4j.ogm.username", System.getProperty("neo4j.ogm.username"));
		properties.put("neo4j.ogm.password", System.getProperty("neo4j.ogm.password"));
		properties.put("neo4j.ogm.datafile", System.getProperty("neo4j.ogm.datafile"));

		bind(Properties.class).annotatedWith(Neo4j.class).toInstance(properties);

	}
}
