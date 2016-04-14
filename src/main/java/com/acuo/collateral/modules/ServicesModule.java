package com.acuo.collateral.modules;

import java.util.Properties;

import com.acuo.collateral.persist.DataLoader;
import com.acuo.collateral.persist.SessionDataLoader;
import com.acuo.collateral.services.ClientService;
import com.acuo.collateral.services.ClientServiceImpl;
import com.acuo.collateral.services.ImportService;
import com.acuo.collateral.services.Neo4jImportService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ServicesModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        install(new Neo4jPersistModule("com.acuo.collateral.persist.entity").properties(getPersistenceProperties()));
        
		bind(DataLoader.class).to(SessionDataLoader.class).in(Singleton.class);
		bind(ClientService.class).to(ClientServiceImpl.class).in(Singleton.class);
		bind(ImportService.class).to(Neo4jImportService.class).in(Singleton.class);        
    }

    private static Properties getPersistenceProperties()
    {
        String url = System.getProperty("neo4j.ogm.url");
        String username = System.getProperty("neo4j.ogm.username");
        String password = System.getProperty("neo4j.ogm.password");
        String driver = System.getProperty("neo4j.ogm.driver");
        String datafile = System.getProperty("neo4j.ogm.datafile");

        Properties properties = new Properties();
        properties.put("neo4j.ogm.driver", driver);
        properties.put("neo4j.ogm.url", url);
        properties.put("neo4j.ogm.username", username);
        properties.put("neo4j.ogm.password", password);
        properties.put("neo4j.ogm.datafile", datafile);

        return properties;
    }
}
