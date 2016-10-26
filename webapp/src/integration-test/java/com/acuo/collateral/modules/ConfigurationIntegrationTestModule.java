package com.acuo.collateral.modules;

import com.acuo.collateral.modules.configuration.PropertiesModule;
import com.acuo.common.app.AppId;
import com.acuo.common.app.Configuration;
import com.acuo.common.app.Environment;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ConfigurationIntegrationTestModule extends AbstractModule {

	@Override
	protected void configure() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(Configuration.class).toInstance(Configuration.builder(AppId.of("webapp")).with(Environment.INTEGRATION).build());
			}
		});
		install(injector.getInstance(PropertiesModule.class));
	}

}
