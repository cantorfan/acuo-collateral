package com.acuo.collateral.services;

import com.acuo.collateral.persist.DataLoader;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Neo4jImportService implements ImportService {

	private final DataLoader loader;
		
	@Inject
	public Neo4jImportService(DataLoader loader) {
		this.loader = loader;		
	}

    @Override
	public void reload() {
    	loader.purgeDatabase();
    	loader.loadAll();
    }   
}
