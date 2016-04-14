package com.acuo.collateral.services;

import java.util.Properties;

import com.acuo.collateral.modules.Neo4j;
import com.acuo.collateral.persist.DataLoader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Neo4jImportService implements ImportService {

	private final DataLoader loader;
	private final Properties persistenceProperties;
	
	@Inject
	public Neo4jImportService(DataLoader loader, @Neo4j Properties persistenceProperties) {
		this.loader = loader;
		this.persistenceProperties = persistenceProperties;
	}

    @Override
	public void reload() {
    	loader.purgeDatabase();
    	loader.loadData(persistenceProperties.getProperty("neo4j.ogm.datafile"));
    }   
}
