package com.acuo.collateral.modules;

import java.util.Properties;

import com.acuo.collateral.persist.DataLoader;
import com.acuo.collateral.persist.SessionDataLoader;
import com.acuo.collateral.services.ClientService;
import com.acuo.collateral.services.ClientServiceImpl;
import com.acuo.collateral.services.FundService;
import com.acuo.collateral.services.FundServiceImpl;
import com.acuo.collateral.services.ImportService;
import com.acuo.collateral.services.Neo4jImportService;
import com.acuo.collateral.services.PortfolioService;
import com.acuo.collateral.services.PortfolioServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class ServicesModule extends AbstractModule
{
    @Override
    protected void configure()
    {
    	bindProperties();
    	
    	install(new Neo4jPersistModule(System.getProperty("neo4j.ogm.packages")));
        
		bind(DataLoader.class).to(SessionDataLoader.class).in(Singleton.class);		
		bind(ImportService.class).to(Neo4jImportService.class).in(Singleton.class);
		bind(ClientService.class).to(ClientServiceImpl.class).in(Singleton.class);
		bind(FundService.class).to(FundServiceImpl.class).in(Singleton.class);
		bind(PortfolioService.class).to(PortfolioServiceImpl.class).in(Singleton.class);
    }

    private void bindProperties()
    {        
        Properties properties = new Properties();        
        properties.put("acuo.data.workdir", System.getProperty("acuo.data.workdir"));
        
        Names.bindProperties(binder(), properties);
        
    }
}
