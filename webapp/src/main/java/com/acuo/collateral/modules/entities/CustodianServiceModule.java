package com.acuo.collateral.modules.entities;

import com.acuo.collateral.services.CustodianService;
import com.acuo.collateral.services.CustodianServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.Singleton;

public class CustodianServiceModule extends AbstractModule implements Module {

    @Override
    protected void configure() {
        bind(CustodianService.class).to(CustodianServiceImpl.class).in(Singleton.class);
    }

}
