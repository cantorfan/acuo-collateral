package com.acuo.collateral;

import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_DATA_DIR;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_CTX_PATH;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_HOST;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_PORT;
import static com.acuo.collateral.modules.configuration.PropertiesHelper.ACUO_WEBAPP_REST_MAPPING_PREFIX;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acuo.common.http.server.HttpResourceHandlerConfig;
import com.acuo.common.http.server.HttpServerConnectorConfig;
import com.acuo.common.http.server.HttpServerWrapperConfig;

public class ResteasyConfigImpl implements ResteasyConfig {

	private static final Logger LOG = LoggerFactory.getLogger(ResteasyConfigImpl.class);

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
		LOG.info("building an http server config with data [{}], context [{}], ip [{}], port [{}], mapping prefix [{}]",
				dataRootDir, contextPath, ipAddress, port, mappingPrefix);

		HttpResourceHandlerConfig withGraphData = new HttpResourceHandlerConfig()
				.withBaseResource(Resource.newClassPathResource(dataRootDir)).withDirectoryListing(true)
				.withContextPath(formatContextPath(dataRootDir));

		HttpServerWrapperConfig config = new HttpServerWrapperConfig().withResourceHandlerConfig(withGraphData)
				.withHttpServerConnectorConfig(HttpServerConnectorConfig.forHttp(ipAddress, port));

		config.setContextPath(formatContextPath(contextPath));

		config.addInitParameter("resteasy.servlet.mapping.prefix", mappingPrefix);

		return config;
	}

	private static String formatContextPath(String contextPath) {
		return contextPath.startsWith("/") ? contextPath : "/" + contextPath;
	}

}
