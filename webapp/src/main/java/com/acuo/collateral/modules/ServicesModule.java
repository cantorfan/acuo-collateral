package com.acuo.collateral.modules;

import com.acuo.collateral.modules.configuration.PropertiesHelper;
import com.acuo.collateral.modules.entities.ClearingHouseServiceModule;
import com.acuo.collateral.modules.entities.ClientServiceModule;
import com.acuo.collateral.modules.entities.CounterpartServiceModule;
import com.acuo.collateral.modules.entities.CustodianServiceModule;
import com.acuo.collateral.modules.entities.ExposureServiceModule;
import com.acuo.collateral.modules.entities.FundServiceModule;
import com.acuo.collateral.modules.entities.PortfolioServiceModule;
import com.acuo.collateral.modules.persistence.DataImporterModule;
import com.acuo.collateral.modules.persistence.DataLoaderModule;
import com.acuo.collateral.modules.persistence.ImportServiceModule;
import com.acuo.collateral.modules.persistence.Neo4jPersistModule;
import com.google.inject.AbstractModule;

public class ServicesModule extends AbstractModule {
	@Override
	protected void configure() {
		install(PropertiesHelper.getPropertiesModule());
		install(new Neo4jPersistModule());
		install(new DataLoaderModule());
		install(new DataImporterModule());
		install(new ImportServiceModule());
		install(new ClientServiceModule());
		install(new FundServiceModule());
		install(new PortfolioServiceModule());
		install(new ExposureServiceModule());
		install(new CustodianServiceModule());
		install(new CounterpartServiceModule());
		install(new ClearingHouseServiceModule());
	}
}
