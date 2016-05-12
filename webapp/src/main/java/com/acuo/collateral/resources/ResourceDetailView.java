package com.acuo.collateral.resources;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.jersey.message.filtering.EntityFiltering;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
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