package com.acuo.collateral.web;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner.GuiceModules;
import com.acuo.collateral.resources.view.Counterpart;
import com.acuo.collateral.resources.view.Exposure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

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

		System.out.println(json);
	}

	@Test
	public void testSerialiseTypedString() throws JsonProcessingException {
		Counterpart counterpart = Counterpart.of("JPM");

		String json = objectMapper.writeValueAsString(counterpart);

	}

	@Test
	public void testDeserialiseTypedString() throws IOException {
		String json = "\"JPM\"";

		Counterpart counterpart = objectMapper.readValue(json, Counterpart.class);

		assertNotNull(counterpart);
	}

}
