package com.acuo.collateral.modules.persistence;

import com.acuo.collateral.services.ImportService;
import com.acuo.collateral.services.Neo4jImportService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ImportServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ImportService.class).to(Neo4jImportService.class).in(Singleton.class);
	}

}
