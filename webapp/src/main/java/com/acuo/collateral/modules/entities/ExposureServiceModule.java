package com.acuo.collateral.modules.entities;

import com.acuo.collateral.services.ExposureService;
import com.acuo.collateral.services.ExposureServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ExposureServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ExposureService.class).to(ExposureServiceImpl.class).in(Singleton.class);
	}

}
