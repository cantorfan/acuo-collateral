package com.acuo.collateral.resources;

import static com.jayway.jsonassert.JsonAssert.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acuo.collateral.modules.Neo4jPersistTestModule;
import com.acuo.collateral.modules.entities.ExposureServiceModule;
import com.acuo.collateral.services.ExposureService;
import com.acuo.collateral.web.JacksonObjectMapperProvider;
import com.acuo.common.util.GuiceJUnitRunner;
import com.acuo.common.util.GuiceJUnitRunner.GuiceModules;
import com.acuo.common.util.WithResteasyFixtures;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ ExposureServiceModule.class, Neo4jPersistTestModule.class })
public class ExposureResourceTest implements WithResteasyFixtures {

	@Inject
	ExposureService exposureService;

	Dispatcher dispatcher;

	@Before
	public void setup() {
		ExposureResource resource = new ExposureResource(exposureService);
		dispatcher = createDispatcher(JacksonObjectMapperProvider.class);
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
		assertThat(response.getContentAsString()).contains("productType").contains("JPM");
	}

	@Test
	public void testFindByClientIdAndAggregateByProductSetAndCount() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/exposures/ByProductSetAndCount/client1");
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		assertThat(response.getContentAsString()).contains("{\"ETD\":1}");
	}

	@Test
	public void testFindByClientIdAndAggregateByCounterPartAndProductType() throws URISyntaxException {
		MockHttpRequest request = MockHttpRequest.get("/exposures/ByCounterPartAndProductType/client1");
		MockHttpResponse response = new MockHttpResponse();

		dispatcher.invoke(request, response);

		assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		String json = response.getContentAsString();

		System.out.println(json);

		assertThat(json).contains("{\"JPM\":{\"FUTURES\":1}}");
		with(json).assertEquals("$.JPM.FUTURES", 1);
	}
}
