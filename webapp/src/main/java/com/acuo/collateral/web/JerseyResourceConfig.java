package com.acuo.collateral.web;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.EncodingFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.inject.Inject;

public class JerseyResourceConfig extends ResourceConfig {
    @Inject
    public JerseyResourceConfig(ServiceLocator locator) {
        // http://stackoverflow.com/questions/35085267/guice-dont-inject-to-jerseys-resources
        this.register(GuiceFeature.class);
        // this.register(EntityFilteringFeature.class);
        this.packages("com.acuo.collateral.resources");

        this.register(LoggingFilter.class);
        this.register(RolesAllowedDynamicFeature.class);
        // this.register(CorsResponseFilter.class);
        this.register(JacksonFeature.class);
        this.register(JacksonObjectMapperProvider.class);
        this.register(Neo4jConnectionExceptionHandler.class);
        EncodingFilter.enableFor(this, GZipEncoder.class);
    }
}
