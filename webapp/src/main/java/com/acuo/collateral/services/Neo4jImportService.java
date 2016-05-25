package com.acuo.collateral.services;

import javax.inject.Inject;

import com.acuo.collateral.persist.DataImporter;
import com.acuo.collateral.persist.DataLoader;
import com.google.inject.Singleton;

@Singleton
public class Neo4jImportService implements ImportService {

	private final DataLoader loader;
	private final DataImporter importer;

	@Inject
	public Neo4jImportService(DataLoader loader, DataImporter importer) {
		this.loader = loader;
		this.importer = importer;
	}

	@Override
	public void reload() {
		loader.purgeDatabase();
		importer.importFiles(DataImporter.ALL_FILES);
	}
}
