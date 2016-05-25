package com.acuo.collateral.persist;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.testutil.TestServer;

import com.acuo.collateral.model.Exposure;
import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.modules.configuration.PropertiesHelper;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner.GuiceModules;
import com.acuo.collateral.services.ExposureService;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ ServicesModule.class })
public class SessionIntegrationTest {

	private static TestServer server;
	private DataImporter importer;
	private CypherExecutor executor;

	@Inject
	@Named(PropertiesHelper.ACUO_DATA_DIR)
	private String workingDirectory;

	@Inject
	@Named(PropertiesHelper.ACUO_CYPHER_DIR_TEMPLATE)
	private String directoryTemplate;

	@Inject
	DataLoader dataLoader;

	@Inject
	ExposureService exposureService;

	@BeforeClass
	public static void setupTestServer() {
		server = new TestServer.Builder().enableAuthentication(false).transactionTimeoutSeconds(2).build();
	}

	@Before
	public void setup() {
		importer = new Neo4jDataImporter(dataLoader, workingDirectory, workingDirectory, directoryTemplate);
		executor = new EmbeddedCypherExecutor(server.getGraphDatabaseService());

		importer.importFiles(DataImporter.ALL_FILES);
	}

	@Test
	public void testFindByClientId() {
		Iterable<Exposure> findByClientId = exposureService.findByClientId("client1");
		for (Exposure exposure : findByClientId) {
			assertNotNull(exposure.getCounterpart());
		}
	}

	@After
	public void teardown() {
		dataLoader.purgeDatabase();
	}

	@AfterClass
	public static void teardownTestServer() {
		server.shutdown();
	}
}
