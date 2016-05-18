package com.acuo.collateral.modules;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.acuo.collateral.modules.PropertiesHelper.IntegrationPropertiesModule;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner;
import com.acuo.collateral.neo4j.utils.GuiceJUnitRunner.GuiceModules;
import com.google.inject.Inject;
import com.google.inject.name.Named;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ IntegrationPropertiesModule.class })
public class PropertiesHelperTest {

	@Inject
	@Named(PropertiesHelper.NEO4J_OGM_URL)
	String neo4jOgmUrl;

	@Inject
	@Named(PropertiesHelper.NEO4J_OGM_USERNAME)
	String neo4jOgmUsername;

	@Inject
	@Named(PropertiesHelper.NEO4J_OGM_PASSWORD)
	String neo4jOgmPassword;

	@Inject
	@Named(PropertiesHelper.NEO4J_OGM_DRIVER)
	String neo4jOgmDriver;

	@Inject
	@Named(PropertiesHelper.NEO4J_OGM_PACKAGES)
	String neo4jOgmPackages;

	@Inject
	@Named(PropertiesHelper.ACUO_DATA_DIR)
	String acuoDataDir;

	@Inject
	@Named(PropertiesHelper.ACUO_WEBAPP_HOST)
	String acuoWebappHost;

	// @Inject
	// @Named(PropertiesHelper.ACUO_WEBAPP_DIR)
	// String acuoWebappRoot;

	@Test
	public void test() {
		assertNotNull(neo4jOgmUrl);
		assertNotNull(neo4jOgmDriver);
		assertNotNull(neo4jOgmUsername);
		assertNotNull(neo4jOgmPassword);
		assertNotNull(neo4jOgmPackages);
		assertNotNull(acuoDataDir);
		// assertNotNull(acuoWebappRoot);
		assertNotNull(acuoWebappHost);
	}

}
