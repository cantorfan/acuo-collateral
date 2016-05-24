package com.acuo.collateral;

import static com.acuo.collateral.modules.PropertiesHelper.ACUO_DATA_DIR;
import static com.acuo.collateral.modules.PropertiesHelper.ACUO_WEBAPP_HOST;
import static com.acuo.collateral.modules.PropertiesHelper.ACUO_WEBAPP_PORT;

import java.util.logging.LogManager;

import javax.servlet.ServletContextListener;

import org.eclipse.jetty.util.resource.Resource;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.acuo.collateral.modules.ResourcesModule;
import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.web.JacksonModule;
import com.acuo.common.http.server.BinderProviderCapture;
import com.acuo.common.http.server.HttpResourceHandlerConfig;
import com.acuo.common.http.server.HttpServerConnectorConfig;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.acuo.common.http.server.HttpServerWrapperFactory;
import com.acuo.common.http.server.HttpServerWrapperModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

class ResteasyMain {

	private static final String DEFAULT_PORT = "8080";

	private static final String DEFAULT_HOST_IP = "127.0.0.1";

	private static final String DEFAULT_DATA_DIR = "/graph-data";

	public static void main(String[] args) throws Exception {

		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();

		int port = Integer.valueOf(System.getProperty(ACUO_WEBAPP_PORT, DEFAULT_PORT));

		String ipAddress = System.getProperty(ACUO_WEBAPP_HOST, DEFAULT_HOST_IP);

		String dataRootDir = System.getProperty(ACUO_DATA_DIR, DEFAULT_DATA_DIR);

		// HttpResourceHandlerConfig withWebFiles = new
		// HttpResourceHandlerConfig()
		// .withWelcomeFiles(Lists.newArrayList("index.html")).withBaseResource(Resource.newClassPathResource("/"))
		// .withDirectoryListing(true).withContextPath("/web");

		HttpResourceHandlerConfig withGraphData = new HttpResourceHandlerConfig()
				.withBaseResource(Resource.newClassPathResource(dataRootDir)).withDirectoryListing(true)
				.withContextPath("/graph-data");

		HttpServerWrapperConfig config = new HttpServerWrapperConfig().withResourceHandlerConfig(withGraphData)
				.withHttpServerConnectorConfig(HttpServerConnectorConfig.forHttp(ipAddress, port));

		BinderProviderCapture<? extends ServletContextListener> listenerProvider = new BinderProviderCapture<>(
				GuiceResteasyBootstrapServletContextListener.class);

		config.addServletContextListenerProvider(listenerProvider);

		config.setContextPath("/acuo");

		config.addInitParameter("resteasy.servlet.mapping.prefix", "/api");

		Injector injector = Guice.createInjector(new ServiceModule(listenerProvider));

		injector.getInstance(HttpServerWrapperFactory.class).getHttpServerWrapper(config).start();
	}

	private static class ServiceModule extends AbstractModule {

		private final BinderProviderCapture<?> listenerProvider;

		public ServiceModule(BinderProviderCapture<?> listenerProvider) {
			this.listenerProvider = listenerProvider;
		}

		@Override
		protected void configure() {
			binder().requireExplicitBindings();

			install(new HttpServerWrapperModule());
			install(new JacksonModule());
			install(new ServicesModule());
			install(new ResourcesModule());

			bind(GuiceResteasyBootstrapServletContextListener.class);

			install(new ServletModule() {
				@Override
				protected void configureServlets() {
					serve("/*").with(HttpServletDispatcher.class);
					bind(HttpServletDispatcher.class).in(Scopes.SINGLETON);
				}
			});

			listenerProvider.saveProvider(binder());
		}

		@Provides
		@Singleton
		JacksonJsonProvider getJacksonJsonProvider(ObjectMapper objectMapper) {
			return new JacksonJsonProvider(objectMapper);
		}
	}
}