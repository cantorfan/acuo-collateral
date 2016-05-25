package com.acuo.collateral.modules.entities;

import com.acuo.collateral.services.ClientService;
import com.acuo.collateral.services.ClientServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ClientServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ClientService.class).to(ClientServiceImpl.class).in(Singleton.class);
	}

}
