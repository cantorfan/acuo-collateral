package com.acuo.collateral.modules;

import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ServicesModule extends AbstractModule {
	@Override
	protected void configure() {
		bindProperties();

		install(new Neo4jPersistModule(System.getProperty("neo4j.ogm.packages")));
		install(new DataLoaderModule());
		install(new ImportServiceModule());
		install(new ClientServiceModule());
		install(new FundServiceModule());
		install(new PortfolioServiceModule());
	}

	private void bindProperties() {
		Properties properties = new Properties();
		properties.put("acuo.data.workdir", System.getProperty("acuo.data.workdir"));
		Names.bindProperties(binder(), properties);
	}
}
