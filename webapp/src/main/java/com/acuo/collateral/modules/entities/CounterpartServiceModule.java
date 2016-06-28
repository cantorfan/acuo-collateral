package com.acuo.collateral.modules.entities;

import com.acuo.collateral.services.CounterpartService;
import com.acuo.collateral.services.CounterpartServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CounterpartServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CounterpartService.class).to(CounterpartServiceImpl.class).in(Singleton.class);
    }

}
