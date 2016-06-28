package com.acuo.collateral.resources;

import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.jersey.message.filtering.EntityFiltering;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EntityFiltering
public @interface ResourceDetailView {
    /**
     * Factory class for creating instances of {@code ProjectDetailedView}
     * annotation.
     */
    public static class Factory extends AnnotationLiteral<ResourceDetailView> implements ResourceDetailView {
        /**
         *
         */
        private static final long serialVersionUID = 6594432054647676535L;

        private Factory() {
        }

        public static ResourceDetailView get() {
            return new Factory();
        }
    }
}