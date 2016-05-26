package com.acuo.collateral;

import java.util.logging.LogManager;

import javax.servlet.ServletContextListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.acuo.collateral.modules.ResourcesModule;
import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.modules.configuration.ConfigurationModule;
import com.acuo.collateral.web.JacksonModule;
import com.acuo.common.http.server.BinderProviderCapture;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.acuo.common.http.server.HttpServerWrapperFactory;
import com.acuo.common.http.server.HttpServerWrapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

class ResteasyMain {

	public static void main(String[] args) throws Exception {

		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();

		BinderProviderCapture<? extends ServletContextListener> listenerProvider = new BinderProviderCapture<>(
				GuiceResteasyBootstrapServletContextListener.class);

		Injector injector = Guice.createInjector(new ServiceModule(listenerProvider));

		ResteasyConfig instance = injector.getInstance(ResteasyConfig.class);
		HttpServerWrapperConfig config = instance.getConfig();

		config.addServletContextListenerProvider(listenerProvider);

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
			install(new ConfigurationModule());
			install(new ServicesModule());
			install(new ResourcesModule());

			bind(GuiceResteasyBootstrapServletContextListener.class);

			bind(ResteasyConfig.class).to(ResteasyConfigImpl.class).in(Singleton.class);

			install(new ServletModule() {
				@Override
				protected void configureServlets() {
					serve("/*").with(HttpServletDispatcher.class);
					bind(HttpServletDispatcher.class).in(Scopes.SINGLETON);
				}
			});

			listenerProvider.saveProvider(binder());
		}
	}
}