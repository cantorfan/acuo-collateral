package com.acuo.collateral.modules;

import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

public abstract class GenericBindings extends AbstractModule {
    protected void addInitializer(Class<? extends AbstractService> initializerClass) {
        Multibinder.newSetBinder(binder(), Service.class).addBinding().to(initializerClass).in(Scopes.SINGLETON);
    }
}