package com.acuo.collateral.web;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;

public final class JacksonModule extends AbstractModule {

	@Override
	protected void configure() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.registerModule(new CustomModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.disable(MapperFeature.USE_GETTERS_AS_SETTERS);

		bind(ObjectMapper.class).toInstance(objectMapper);
	}

}
