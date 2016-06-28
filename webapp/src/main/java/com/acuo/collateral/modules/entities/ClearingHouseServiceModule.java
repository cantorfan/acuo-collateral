package com.acuo.collateral.modules.entities;

import com.acuo.collateral.services.ClearingHouseService;
import com.acuo.collateral.services.ClearingHouseServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ClearingHouseServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ClearingHouseService.class).to(ClearingHouseServiceImpl.class).in(Singleton.class);
    }

}
