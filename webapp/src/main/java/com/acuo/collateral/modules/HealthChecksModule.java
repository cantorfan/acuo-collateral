package com.acuo.collateral.modules;

import com.acuo.collateral.metrics.Neo4jHealthCheck;
import com.acuo.common.metrics.DiskSpaceHealthCheck;
import com.acuo.common.metrics.PingHealthCheck;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.concurrent.TimeUnit;

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
    private Client jaxrsClient() {
        /*
        ClientBuilder.newBuilder()
                .property("connection.timeout", 10000)
                .register(JacksonJsonProvider.class)
                .newClient()
         */
        return new ResteasyClientBuilder()
                .establishConnectionTimeout(2, TimeUnit.SECONDS)
                .connectionPoolSize(10)
                .socketTimeout(10, TimeUnit.SECONDS)
                .register(JacksonJsonProvider.class)
                .build();
    }
}