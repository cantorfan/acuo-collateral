package com.acuo.collateral.modules.persistence;

import com.acuo.collateral.persist.DataLoader;
import com.acuo.collateral.persist.SessionDataLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class DataLoaderModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataLoader.class).to(SessionDataLoader.class).in(Singleton.class);
    }

}