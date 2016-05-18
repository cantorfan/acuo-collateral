package com.acuo.collateral.modules;

import com.acuo.collateral.persist.DataImporter;
import com.acuo.collateral.persist.Neo4jDataImporter;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DataImporterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataImporter.class).to(Neo4jDataImporter.class).in(Singleton.class);
	}

}
