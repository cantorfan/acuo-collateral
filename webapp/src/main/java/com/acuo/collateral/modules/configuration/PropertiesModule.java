package com.acuo.collateral.modules.configuration;

import com.google.inject.AbstractModule;

public class PropertiesModule extends AbstractModule {

	private Configuration configuration = Configuration.builder(AppId.of("webapp")).build();

	@Override
	protected void configure() {
		PropertiesHelper helper = PropertiesHelper.of(configuration);
		install(new com.smokejumperit.guice.properties.PropertiesModule(helper.getDefaultProperties(),
				helper.getOverrides()));
	}

}