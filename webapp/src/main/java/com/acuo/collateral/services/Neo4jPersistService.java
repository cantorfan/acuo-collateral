package com.acuo.collateral.services;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import org.neo4j.ogm.authentication.UsernamePasswordCredentials;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.service.Components;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acuo.collateral.modules.configuration.PropertiesHelper;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

@Singleton
public class Neo4jPersistService implements Provider<Session>, UnitOfWork, PersistService {

	private static final Logger LOG = LoggerFactory.getLogger(Neo4jPersistService.class);

	private ThreadLocal<Session> sessions;
	private SessionFactory sessionFactory;

	@Inject
	Neo4jPersistService(@Named(PropertiesHelper.NEO4J_OGM_DRIVER) String driver,
			@Named(PropertiesHelper.NEO4J_OGM_URL) String url,
			@Named(PropertiesHelper.NEO4J_OGM_USERNAME) String userName,
			@Named(PropertiesHelper.NEO4J_OGM_PASSWORD) String password,
			@Named(PropertiesHelper.NEO4J_OGM_PACKAGES) String packages) {
		LOG.info("Creating a Neo4j persistence service using driver [{}] and url[{}]", driver, url);
		sessions = new ThreadLocal<>();
		Configuration configuration = Components.configuration();
		configuration.driverConfiguration().setDriverClassName(driver).setURI(url)
				.setCredentials(new UsernamePasswordCredentials(userName, password));
		this.sessionFactory = new SessionFactory(packages);
	}

	@Override
	public Session get() {
		if (!isWorking()) {
			begin();
		}

		Session session = sessions.get();

		if (session == null) {
			throw new IllegalStateException("Requested Session outside work unit. "
					+ "Try calling UnitOfWork.begin() first, or use a PersistFilter if you "
					+ "are inside a servlet environment.");
		}

		return session;
	}

	public boolean isWorking() {
		return sessions.get() != null;
	}

	@Override
	public void start() {
		// Do nothing...
	}

	@Override
	public void stop() {
		// Do nothing...
	}

	@Override
	public void begin() {
		if (sessions.get() != null) {
			throw new IllegalStateException(
					"Work already begun on this thread. Looks like you have called UnitOfWork.begin() twice"
							+ " without a balancing call to end() in between.");
		}

		sessions.set(sessionFactory.openSession());
	}

	@Override
	public void end() {
		Session session = sessions.get();

		// Let's not penalize users for calling end() multiple times.
		if (session == null) {
			return;
		}

		// session.close();
		sessions.remove();
	}
}
