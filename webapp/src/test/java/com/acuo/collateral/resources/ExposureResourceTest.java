package com.acuo.collateral.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acuo.collateral.modules.ExposureServiceModule;
import com.acuo.collateral.modules.Neo4jPersistTestModule;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner.GuiceModules;
import com.acuo.collateral.services.ExposureService;
import com.google.inject.Inject;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ ExposureServiceModule.class, Neo4jPersistTestModule.class })
public class ExposureResourceTest {

	@Inject
	ExposureService exposureService;

	Dispatcher dispatcher;

	@Before
	public void setup() {
		ExposureResource resource = new ExposureResource(exposureService);
		dispatcher = MockDispatcherFactory.createDispatcher();
		dispatcher.getRegistry().addSingletonResource(resource);

	}

	@Test
	public void testFindAll() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/exposures");
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		assertThat(response.getContentAsString()).contains("productType");
	}

	@Test
	public void testFindById() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/exposures/1");
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		assertThat(response.getContentAsString()).contains("productType").contains("counterpartId");
		System.out.println(response.getContentAsString());
	}
}
