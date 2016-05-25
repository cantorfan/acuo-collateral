package com.acuo.collateral;

import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_DATA_DIR;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_CTX_PATH;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_HOST;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_PORT;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_REST_MAPPING_PREFIX;

import org.eclipse.jetty.util.resource.Resource;

import com.acuo.common.http.server.HttpResourceHandlerConfig;
import com.acuo.common.http.server.HttpServerConnectorConfig;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ResteasyConfigImpl implements ResteasyConfig {

	@Inject
	@Named(ACUO_WEBAPP_PORT)
	public Integer port;

	@Inject
	@Named(ACUO_WEBAPP_HOST)
	public String ipAddress;

	@Inject
	@Named(ACUO_DATA_DIR)
	public String dataRootDir;

	@Inject
	@Named(ACUO_WEBAPP_CTX_PATH)
	public String contextPath;

	@Inject
	@Named(ACUO_WEBAPP_REST_MAPPING_PREFIX)
	public String mappingPrefix;

	@Override
	public HttpServerWrapperConfig getConfig() {
		HttpResourceHandlerConfig withGraphData = new HttpResourceHandlerConfig()
				.withBaseResource(Resource.newClassPathResource(dataRootDir)).withDirectoryListing(true)
				.withContextPath(dataRootDir);

		HttpServerWrapperConfig config = new HttpServerWrapperConfig().withResourceHandlerConfig(withGraphData)
				.withHttpServerConnectorConfig(HttpServerConnectorConfig.forHttp(ipAddress, port));

		config.setContextPath(contextPath);

		config.addInitParameter("resteasy.servlet.mapping.prefix", mappingPrefix);

		return config;
	}

}
