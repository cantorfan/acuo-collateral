package com.acuo.collateral.modules;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.smokejumperit.guice.properties.PropertiesModule;

public class PropertiesHelper {

	private static String PROPERTIES_DIR_TEMPLATE = "/config/%s/%s";
	private static String DEFAULTS_PROPERTIES_FILE = "defaults.properties";
	private static String OVERRIDES_PROPERTIES_FILE = "overrides.properties";

	public static class IntegrationPropertiesModule extends PropertiesModule {

		public IntegrationPropertiesModule() {
			super(getDefaultProperties(), getOverrides());
		}

		private static Properties getOverrides() {
			return getPropertiesFrom("INT", OVERRIDES_PROPERTIES_FILE);
		}

		private static Properties getDefaultProperties() {
			return getPropertiesFrom("INT", DEFAULTS_PROPERTIES_FILE);
		}
	}

	static Properties getPropertiesFrom(String env, String fileName) {
		String propertiesFilePath = String.format(PROPERTIES_DIR_TEMPLATE, env, fileName);
		final Properties properties = new Properties();
		try (final InputStream stream = PropertiesHelper.class.getResourceAsStream(propertiesFilePath)) {
			properties.load(stream);
		} catch (IOException e) {
		}
		return properties;
	}
}
