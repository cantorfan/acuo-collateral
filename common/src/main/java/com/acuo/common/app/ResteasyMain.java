package com.acuo.common.app;

import java.util.Collection;
import java.util.logging.LogManager;

import javax.servlet.ServletContextListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.acuo.common.http.server.BinderProviderCapture;
import com.acuo.common.http.server.HttpServerWrapperConfig;
import com.acuo.common.http.server.HttpServerWrapperFactory;
import com.acuo.common.http.server.HttpServerWrapperModule;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public abstract class ResteasyMain {

	private static final Logger LOG = LoggerFactory.getLogger(ResteasyMain.class);

	public ResteasyMain() {
		LogManager.getLogManager().reset();
		SLF4JBridgeHandler.install();

		BinderProviderCapture<? extends ServletContextListener> listenerProvider = new BinderProviderCapture<>(
				GuiceResteasyBootstrapServletContextListener.class);

		Injector injector = Guice.createInjector(new ServiceModule(listenerProvider, providers(), modules(), config()));

		ResteasyConfig instance = injector.getInstance(ResteasyConfig.class);
		HttpServerWrapperConfig config = instance.getConfig();

		config.addServletContextListenerProvider(listenerProvider);

		try {
			injector.getInstance(HttpServerWrapperFactory.class).getHttpServerWrapper(config).start();
		} catch (Exception e) {
			LOG.error("Error initializing Resteasy application", e);
		}
	}

	public abstract Class<? extends ResteasyConfig> config();

	public abstract Collection<Class<?>> providers();

	public abstract Collection<Module> modules();

	private static class ServiceModule extends AbstractModule {

		private final BinderProviderCapture<?> listenerProvider;
		private final Collection<Class<?>> providers;
		private final Collection<Module> modules;
		private final Class<? extends ResteasyConfig> config;

		public ServiceModule(BinderProviderCapture<?> listenerProvider, Collection<Class<?>> providers,
				Collection<Module> modules, Class<? extends ResteasyConfig> config) {
			this.listenerProvider = listenerProvider;
			this.providers = providers;
			this.modules = modules;
			this.config = config;
		}

		@Override
		protected void configure() {
			binder().requireExplicitBindings();

			providers.stream().forEach(this::bind);

			install(new HttpServerWrapperModule());

			modules.stream().forEach(this::install);

			bind(GuiceResteasyBootstrapServletContextListener.class);

			bind(ResteasyConfig.class).to(config).in(Singleton.class);

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
