package com.acuo.collateral.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acuo.collateral.modules.JacksonModule;
import com.acuo.collateral.resources.view.Counterpart;
import com.acuo.collateral.resources.view.Exposure;
import com.acuo.common.util.GuiceJUnitRunner;
import com.acuo.common.util.GuiceJUnitRunner.GuiceModules;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(value = GuiceJUnitRunner.class)
@GuiceModules(value = { JacksonModule.class })
public class JacksonTest {

	@Inject
	ObjectMapper objectMapper;

	private Map<Counterpart, Exposure> data = new HashMap<Counterpart, Exposure>();

	@Before
	public void setup() {
		data.put(Counterpart.of("JPM"), new Exposure());
	}

	@Test
	public void testSerialisingMap() throws JsonProcessingException {
		String json = objectMapper.writeValueAsString(data);
		assertThat(json, is(notNullValue()));
	}

	@Test
	public void testSerialiseTypedString() throws JsonProcessingException {
		Counterpart counterpart = Counterpart.of("JPM");

		String json = objectMapper.writeValueAsString(counterpart);
		assertThat(json, is(notNullValue()));

	}

	@Test
	public void testDeserialiseTypedString() throws IOException {
		String json = "\"JPM\"";

		Counterpart counterpart = objectMapper.readValue(json, Counterpart.class);
		assertThat(counterpart, is(notNullValue()));
	}

}
