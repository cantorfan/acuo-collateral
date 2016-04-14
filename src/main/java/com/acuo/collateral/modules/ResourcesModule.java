package com.acuo.collateral.modules;
import com.acuo.collateral.resources.ClientResource;
import com.acuo.collateral.resources.ImportResource;
import com.google.inject.AbstractModule;

/**
 * Created by aurel.avramescu on 14/06/2014.
 */
public class ResourcesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ClientResource.class);
        bind(ImportResource.class);
    }
}