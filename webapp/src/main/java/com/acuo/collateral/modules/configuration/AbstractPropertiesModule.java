package com.acuo.collateral.modules.configuration;

import java.util.Properties;

import com.smokejumperit.guice.properties.PropertiesModule;

public class AbstractPropertiesModule extends PropertiesModule {

	public AbstractPropertiesModule(String env) {
		super(getDefaultProperties(env), getOverrides(env));
	}

	private static Properties getOverrides(String env) {
		return PropertiesHelper.getPropertiesFrom(env, PropertiesHelper.OVERRIDES_PROPERTIES_FILE);
	}

	private static Properties getDefaultProperties(String env) {
		return PropertiesHelper.getPropertiesFrom(env, PropertiesHelper.DEFAULTS_PROPERTIES_FILE);
	}
}