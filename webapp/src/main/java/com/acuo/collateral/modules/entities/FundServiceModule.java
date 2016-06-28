package com.acuo.collateral.modules.entities;

import com.acuo.collateral.services.FundService;
import com.acuo.collateral.services.FundServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class FundServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FundService.class).to(FundServiceImpl.class).in(Singleton.class);
    }

}
