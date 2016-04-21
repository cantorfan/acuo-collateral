package com.acuo.collateral.modules;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.acuo.collateral.persist.DataLoader;
import com.acuo.collateral.services.ClientService;
import com.acuo.collateral.services.FundService;
import com.acuo.collateral.services.ImportService;
import com.acuo.collateral.services.PortfolioService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public abstract class ServicesTestModule extends AbstractModule {

	@Mock
	DataLoader dataLoader;

	@Mock
	ImportService importService;

	@Mock
	ClientService clientService;

	@Mock
	FundService fundService;

	@Mock
	PortfolioService portfolioService;

	@Override
	protected void configure() {
		MockitoAnnotations.initMocks(this);
	}

	@Provides
	protected DataLoader provideDataLoader() {
		return dataLoader;
	}

	@Provides
	protected ImportService provideImportService() {
		return importService;
	}

	@Provides
	protected ClientService provideClientService() {
		return clientService;
	}

	@Provides
	protected FundService provideFundService() {
		return fundService;
	}

	@Provides
	protected PortfolioService providePortfolioService() {
		return portfolioService;
	}
}
