package com.acuo.collateral.modules;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.services.ClientServiceImplTest;
import com.acuo.collateral.services.ExposureServiceImplTest;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class Neo4jPersistTestModule extends AbstractModule {

	@Mock
	Session session;

	@Override
	protected void configure() {
		MockitoAnnotations.initMocks(this);
	}

	@Provides
	Session provideSession() {
		ClientServiceImplTest.stub(session);
		ExposureServiceImplTest.stub(session);
		return session;
	}

}
