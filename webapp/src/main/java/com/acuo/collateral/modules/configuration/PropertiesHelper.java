package com.acuo.collateral.modules.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.smokejumperit.guice.properties.PropertiesModule;

public class PropertiesHelper {

	private static final String PROPERTIES_DIR_TEMPLATE = "/config/%s/%s";
	static final String DEFAULTS_PROPERTIES_FILE = "defaults.properties";
	static final String OVERRIDES_PROPERTIES_FILE = "overrides.properties";

	public static final String NEO4J_OGM_URL = "neo4j.ogm.url";
	public static final String NEO4J_OGM_USERNAME = "neo4j.ogm.username";
	public static final String NEO4J_OGM_PASSWORD = "neo4j.ogm.password";
	public static final String NEO4J_OGM_DRIVER = "neo4j.ogm.driver";
	public static final String NEO4J_OGM_PACKAGES = "neo4j.ogm.packages";

	public static final String ACUO_DATA_DIR = "acuo.data.dir";
	public static final String ACUO_DATA_IMPORT_LINK = "acuo.data.import.link";
	public static final String ACUO_CYPHER_DIR_TEMPLATE = "acuo.cypher.dir.template";
	public static final String ACUO_WEBAPP_HOST = "acuo.webapp.host";
	public static final String ACUO_WEBAPP_PORT = "acuo.webapp.port";
	public static final String ACUO_WEBAPP_DIR = "acuo.webapp.dir";
	public static final String ACUO_WEBAPP_CTX_PATH = "acuo.webapp.context.path";
	public static final String ACUO_WEBAPP_REST_MAPPING_PREFIX = "acuo.webapp.rest.servlet.mapping.prefix";

	public static PropertiesModule getPropertiesModule() {
		return new IntegrationPropertiesModule();
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
