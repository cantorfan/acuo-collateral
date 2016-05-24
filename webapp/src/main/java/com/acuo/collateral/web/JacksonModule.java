package com.acuo.collateral.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;

public final class JacksonModule extends AbstractModule {

	@Override
	protected void configure() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new CustomModule());
		SerializationConfig config = objectMapper.getSerializationConfig()
				.withoutFeatures(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		objectMapper.setConfig(config);

		bind(ObjectMapper.class).toInstance(objectMapper);
	}

}
