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
	@Named("neo4j.ogm.url")
	String neo4jOgmUrl;

	@Inject
	@Named("neo4j.ogm.username")
	String neo4jOgmUsername;

	@Inject
	@Named("neo4j.ogm.password")
	String neo4jOgmPassword;

	@Inject
	@Named("neo4j.ogm.driver")
	String neo4jOgmDriver;

	@Inject
	@Named("neo4j.ogm.packages")
	String neo4jOgmPackages;

	@Inject
	@Named("acuo.data.dir")
	String acuoDataDir;

	@Inject
	@Named("acuo.webapp.host")
	String acuoWebappHost;

	@Inject
	@Named("acuo.webapp.root")
	String acuoWebappRoot;

	@Test
	public void test() {
		assertNotNull(neo4jOgmUrl);
		assertNotNull(neo4jOgmDriver);
		assertNotNull(neo4jOgmUsername);
		assertNotNull(neo4jOgmPassword);
		assertNotNull(neo4jOgmPackages);
		assertNotNull(acuoDataDir);
		assertNotNull(acuoWebappRoot);
		assertNotNull(acuoWebappHost);
	}

}
