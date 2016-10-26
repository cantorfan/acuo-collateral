package com.acuo.collateral;

import com.acuo.collateral.modules.HealthChecksModule;
import com.acuo.collateral.modules.JacksonModule;
import com.acuo.collateral.modules.ResourcesModule;
import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.modules.configuration.ConfigurationModule;
import com.acuo.common.app.ResteasyConfig;
import com.acuo.common.app.ResteasyMain;
import com.google.common.util.concurrent.Service;
import com.google.inject.Module;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class DataApp extends ResteasyMain {

    public static void main(String[] args) throws Exception {
        Service service = new DataApp();
        service.startAsync();
    }

    @Override
    public Class<? extends ResteasyConfig> config() {
        return ResteasyConfigImpl.class;
    }

    @Override
    public Collection<Class<?>> providers() {
        return Collections.emptyList();
    }

    @Override
    public Collection<Module> modules() {
        return Arrays.asList(new JacksonModule(),
                new ConfigurationModule(),
                new ServicesModule(),
                new ResourcesModule(),
                new HealthChecksModule());
    }
}