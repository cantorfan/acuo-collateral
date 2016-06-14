package com.acuo.common.marshal.jaxb;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.acuo.common.marshal.Marshaller;
import com.acuo.common.util.ResourceFile;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class JaxbMarshallerTest {

	@Rule
	public ResourceFile res = new ResourceFile("/some.xml");

	@Inject
	JaxbMarshaller marshaller;

	@Before
	public void init() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(JaxbContextFactory.class).to(MoxyJaxbContextFactory.class);
				bind(Marshaller.class).to(JaxbMarshaller.class);
			}

			@Provides
			@JaxbTypes
			List<Class<?>> types() {
				return Arrays.asList(new Class<?>[] { SomeXml.class });
			}
		});
		injector.injectMembers(this);
	}

	@Test
	public void testDoMarshal() throws Exception {
		String xml = marshaller.doMarshal(someXml());
		assertNotNull(xml);
		assertNotEquals(0, xml.length());
	}

	@Test
	public void testDoUnmarshal() throws Exception {
		SomeXml someXml = marshaller.doUnmarshal(res.getContent(), SomeXml.class);
		assertNotNull(someXml);
	}

	@Test
	public void testXmlFilesAreEqual() throws Exception {
		String xml = marshaller.doMarshal(someXml());
		assertEquals(res.getContent(), xml);
	}

	@Test
	public void testObjectsAreEqual() throws Exception {
		SomeXml xml = marshaller.doUnmarshal(res.getContent(), SomeXml.class);
		assertEquals(someXml(), xml);
	}

	private SomeXml someXml() throws Exception {
		SomeXml someXml = new SomeXml();
		someXml.header.name = "SomeXml";
		someXml.header.date = new DateAdapter().unmarshal("2016-06-14");

		asList(1l, 2l, 3l, 4l, 5l).stream().map(JaxbMarshallerTest::addValue).forEach(v -> someXml.values.add(v));

		return someXml;
	}

	private static SomeXml.Value addValue(Long l) {
		SomeXml.Value value = new SomeXml.Value();
		value.id = l;
		return value;
	}

}
