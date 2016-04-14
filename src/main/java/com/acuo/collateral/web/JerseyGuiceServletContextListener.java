package com.acuo.collateral.web;

import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.modules.ResourcesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class JerseyGuiceServletContextListener extends GuiceServletContextListener
{
    static Injector injector;

    @Override
    protected Injector getInjector()
    {
        injector = Guice.createInjector(new ServicesModule(), new ResourcesModule());
        return injector;
    }
}
