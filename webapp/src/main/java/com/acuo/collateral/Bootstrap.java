package com.acuo.collateral;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acuo.collateral.modules.PropertiesHelper;
import com.acuo.collateral.web.JerseyGuiceServletContextListener;
import com.acuo.collateral.web.JerseyResourceConfig;

public class Bootstrap {

	private static Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

	private static final String DEFAULT_WEBAPP_DIR = "src/main/webapp/";

	private static final String DEFAULT_DATA_DIR = "src/main/resources/";

	private static final String DEFAULT_PORT = "8080";

	private static final String DEFAULT_HOST_IP = "127.0.0.1";

	private static final String DEFAULT_URL_PATTERN = "/api/*";

	public static void main(String[] args) {
		int port = Integer.valueOf(System.getProperty(PropertiesHelper.ACUO_WEBAPP_PORT, DEFAULT_PORT));

		URI httpUri = UriBuilder.fromPath("/").scheme("http")
				.host(System.getProperty(PropertiesHelper.ACUO_WEBAPP_HOST, DEFAULT_HOST_IP)).port(port).build();
		HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(httpUri, false);

		WebappContext webappContext = new WebappContext("Acuo Neo4j POC", "/acuo");

		webappContext.addListener(JerseyGuiceServletContextListener.class);

		ServletRegistration servletRegistration = webappContext.addServlet("ServletContainer", ServletContainer.class);
		servletRegistration.setInitParameter("javax.ws.rs.Application", JerseyResourceConfig.class.getName());
		servletRegistration.addMapping(DEFAULT_URL_PATTERN);

		String webappRootDir = System.getProperty(PropertiesHelper.ACUO_WEBAPP_DIR, DEFAULT_WEBAPP_DIR);
		httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler(webappRootDir), "/");

		String dataRootDir = System.getProperty(PropertiesHelper.ACUO_DATA_DIR, DEFAULT_DATA_DIR);
		httpServer.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(Bootstrap.class.getClassLoader()),
				"/data");

		webappContext.deploy(httpServer);

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					httpServer.shutdownNow();
				} catch (Exception e) {
					LOG.error("Error during the stopping of Jetty Web Server. See exception.", e);
				}
			}
		});

		LOG.info("Starting httpServer...");
		try {
			httpServer.start();
		} catch (IOException ioe) {
			LOG.error("Failed to start httpServer.", ioe);
		}
	}
}
