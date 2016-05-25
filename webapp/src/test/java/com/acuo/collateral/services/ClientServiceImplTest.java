package com.acuo.collateral.services;

import static com.acuo.common.TestHelper.matchesRegex;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.model.Client;
import com.acuo.collateral.modules.Neo4jPersistTestModule;
import com.acuo.collateral.modules.entities.ClientServiceModule;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner.GuiceModules;
import com.google.inject.Inject;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ ClientServiceModule.class, Neo4jPersistTestModule.class })
public class ClientServiceImplTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Inject
	ClientService clientService;

	public static void stub(Session session) {
		when(session.loadAll(Client.class, GenericService.DEPTH_LIST))
				.thenReturn(Collections.singletonList(mock(Client.class)));
	}

	@Test
	public void testFindAll() {
		Iterable<Client> all = clientService.findAll();

		assertNotNull("list returned from findAll shouldn't be null", all);
		assertTrue(all.iterator().hasNext());
	}

	@Test
	public void testFindWithNullParameter() {
		clientService.find(null);
	}

	@Test
	public void testDeleteWithNullParameter() {
		clientService.delete(null);
	}

	@Test
	public void testCreateOrUpdateWithNullParameter() {
		// clientService.createOrUpdate(mock(Client.class));
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage(matchesRegex(".*'entity'.*null.*"));
		clientService.createOrUpdate(null);
	}
}
