package com.acuo.collateral.resources;

import javax.annotation.Priority;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Inject;
import com.google.inject.Injector;

@Priority(1)
public class GuiceInMemoryFeature implements Feature {

	@Inject
	Injector injector;

	@Override
	public boolean configure(FeatureContext context) {
		ServiceLocator locator = ServiceLocatorProvider.getServiceLocator(context);
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(locator);
		// Injector injector = Guice.createInjector(new GuiceInMemoryModule());
		GuiceIntoHK2Bridge guiceBridge = locator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(injector);
		return true;
	}
}