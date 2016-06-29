package com.acuo.collateral.modules;

import com.acuo.collateral.metrics.Neo4jHealthCheck;
import com.acuo.common.metrics.DiskSpaceHealthCheck;
import com.acuo.common.metrics.PingHealthCheck;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.health.HealthCheck;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class HealthChecksModule extends AbstractModule {
    @Override
    protected void configure() {

        final Multibinder<MetricSet> metricSetBinder = Multibinder.newSetBinder(binder(), MetricSet.class);
        //metricSetBinder.addBinding().toInstance(new VmSpecsMetricSet());

        final Multibinder<HealthCheck> healthChecksBinder = Multibinder.newSetBinder(binder(), HealthCheck.class);
        healthChecksBinder.addBinding().to(PingHealthCheck.class).asEagerSingleton();
        healthChecksBinder.addBinding().to(DiskSpaceHealthCheck.class).asEagerSingleton();
        healthChecksBinder.addBinding().to(Neo4jHealthCheck.class).asEagerSingleton();
    }

    @Provides
    Client getClient() {
        ClientConfig config = new ClientConfig();
        config.register(new JacksonFeature());
        config.property(ClientProperties.ASYNC_THREADPOOL_SIZE, 10);
        config.property(ClientProperties.CONNECT_TIMEOUT, 10000);
        config.property(ClientProperties.READ_TIMEOUT, 10000);
        Client client = ClientBuilder.newClient(config);
        return client;
    }
}