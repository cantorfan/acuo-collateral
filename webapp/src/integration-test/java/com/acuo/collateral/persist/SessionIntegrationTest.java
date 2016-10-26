package com.acuo.collateral.persist;

import com.acuo.collateral.model.Exposure;
import com.acuo.collateral.modules.ConfigurationIntegrationTestModule;
import com.acuo.collateral.modules.Neo4jIntegrationTestModule;
import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.modules.configuration.PropertiesHelper;
import com.acuo.collateral.services.ExposureService;
import com.acuo.common.app.ServiceManagerModule;
import com.acuo.common.util.GuiceJUnitRunner;
import com.acuo.common.util.GuiceJUnitRunner.GuiceModules;
import com.google.common.util.concurrent.ServiceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.inject.Named;

import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ ConfigurationIntegrationTestModule.class, ServicesModule.class, ServiceManagerModule.class, Neo4jIntegrationTestModule.class})
public class SessionIntegrationTest {

	private DataImporter importer;

	@Inject
	ServiceManager serviceManager;

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

	@Before
	public void setup() {
		if (!serviceManager.isHealthy()) {
			serviceManager.startAsync().awaitHealthy();
		}

		importer = new Neo4jDataImporter(dataLoader, workingDirectory, workingDirectory, directoryTemplate);
		importer.importFiles(DataImporter.ALL_FILES);
	}

	@Test
	public void testFindByClientId() {
		Iterable<Exposure> findByClientId = exposureService.findByClientId("client1");
		for (Exposure exposure : findByClientId) {
			assertNotNull(exposure.getCounterpart());
		}
	}

	@Test
	public void testFindAll() {
		Iterable<Exposure> all = exposureService.findAll();
		for (Exposure exposure : all) {
			assertNotNull(exposure.getCounterpart());
		}
	}

	@After
	public void teardown() {
		dataLoader.purgeDatabase();
	}
}
