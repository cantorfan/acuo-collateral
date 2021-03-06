package com.acuo.collateral.services;

import static com.acuo.common.TestHelper.matchesRegex;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.model.Counterpart;
import com.acuo.collateral.model.Exposure;
import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.modules.Neo4jPersistTestModule;
import com.acuo.collateral.modules.entities.ExposureServiceModule;
import com.acuo.common.util.GuiceJUnitRunner;
import com.acuo.common.util.GuiceJUnitRunner.GuiceModules;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ ExposureServiceModule.class, Neo4jPersistTestModule.class })
public class ExposureServiceImplTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Inject
	ExposureService exposureService;

	@SuppressWarnings("unchecked")
	public static void stub(Session session) {
		Exposure mockExposure = mock(Exposure.class);
		Counterpart mockCounterpart = mock(Counterpart.class);
		when(session.loadAll(Exposure.class, GenericService.DEPTH_LIST))
				.thenReturn(Collections.singletonList(mockExposure));
		when(session.load(Exposure.class, 1l, GenericService.DEPTH_ENTITY)).thenReturn(mockExposure);
		when(session.query(Matchers.<Class<Exposure>> any(), any(String.class), any(Map.class)))
				.thenReturn(Collections.singletonList(mockExposure));
		when(mockCounterpart.getName()).thenReturn("JPM");
		when(mockExposure.getCounterpart()).thenReturn(mockCounterpart);
		when(mockExposure.getProductType()).thenReturn(ProductType.FUTURES);
	}

	@Test
	public void testFindAll() {
		Iterable<Exposure> all = exposureService.findAll();

		assertNotNull("list returned from findAll shouldn't be null", all);
		assertTrue(all.iterator().hasNext());
	}

	@Test
	public void testFindWithNullParameter() {
		exposureService.find(null);
	}

	@Test
	public void testDeleteWithNullParameter() {
		exposureService.delete(null);
	}

	@Test
	public void testCreateOrUpdateWithNullParameter() {
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'entity'.*null.*"));
		exposureService.createOrUpdate(null);
	}

	@Test
	public void testFindByClientIdAndGroupByCounterpart() {
		String clientId = "client1";
		Iterable<Exposure> results = exposureService.findByClientId(clientId);
		assertThat(results, is(notNullValue()));
	}

}
