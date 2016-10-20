package com.acuo.collateral;

import com.acuo.common.app.ResteasyConfig;
import com.acuo.common.http.server.HttpResourceHandlerConfig;
import com.acuo.common.http.server.HttpServerConnectorConfig;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

import static com.acuo.collateral.modules.configuration.PropertiesHelper.*;

@Slf4j
public class ResteasyConfigImpl implements ResteasyConfig {

    private final Integer port;
    private final String ipAddress;
    private final String dataRootDir;
    private final String contextPath;
    private final String mappingPrefix;

    @Inject
    public ResteasyConfigImpl(@Named(ACUO_WEBAPP_PORT) Integer port,
                              @Named(ACUO_WEBAPP_HOST) String ipAddress,
                              @Named(ACUO_DATA_DIR) String dataRootDir,
                              @Named(ACUO_WEBAPP_CTX_PATH) String contextPath,
                              @Named(ACUO_WEBAPP_REST_MAPPING_PREFIX) String mappingPrefix) {
        this.port = port;
        this.ipAddress = ipAddress;
        this.dataRootDir = dataRootDir;
        this.contextPath = contextPath;
        this.mappingPrefix = mappingPrefix;
    }

    @Override
    public HttpServerWrapperConfig getConfig() {
        log.info("building an http server config with data [{}], context [{}], ip [{}], port [{}], mapping prefix [{}]",
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
