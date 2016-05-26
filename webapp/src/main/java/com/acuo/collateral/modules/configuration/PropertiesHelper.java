package com.acuo.collateral.modules.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.acuo.common.util.ArgChecker;

public class PropertiesHelper {

	private static final String DEFAULTS_PROPERTIES_TEMPLATE = "/%s.properties";
	private static final String OVERRIDES_PROPERTIES_TEMPLATE = "/%s-%s.properties";

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

	private final Configuration configuration;

	private PropertiesHelper(Configuration configuration) {
		this.configuration = configuration;
		configuration.getAppId();
	}

	public static PropertiesHelper of(Configuration configuration) {
		ArgChecker.notNull(configuration, "configuration");
		return new PropertiesHelper(configuration);
	}

	public Properties getOverrides() {
		return getPropertiesFrom(overrideFilePath());
	}

	public Properties getDefaultProperties() {
		return getPropertiesFrom(defaultFilePath());
	}

	private String overrideFilePath() {
		AppId appId = configuration.getAppId();
		Environment environment = configuration.getEnvironment();
		return String.format(OVERRIDES_PROPERTIES_TEMPLATE, appId.toString(), environment.toString());
	}

	private String defaultFilePath() {
		AppId appId = configuration.getAppId();
		return String.format(DEFAULTS_PROPERTIES_TEMPLATE, appId.toString());
	}

	private Properties getPropertiesFrom(String propertiesFilePath) {
		final Properties properties = new Properties();
		try (final InputStream stream = PropertiesHelper.class.getResourceAsStream(propertiesFilePath)) {
			if (stream != null)
				properties.load(stream);
		} catch (IOException e) {
		}
		return properties;
	}
}