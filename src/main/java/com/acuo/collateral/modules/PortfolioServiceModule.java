package com.acuo.collateral.modules;

import com.acuo.collateral.services.PortfolioService;
import com.acuo.collateral.services.PortfolioServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class PortfolioServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PortfolioService.class).to(PortfolioServiceImpl.class).in(Singleton.class);
	}

}
