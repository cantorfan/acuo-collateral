package com.acuo.collateral.persist;

import com.acuo.collateral.model.*;
import com.acuo.collateral.modules.ConfigurationIntegrationTestModule;
import com.acuo.collateral.modules.Neo4jIntegrationTestModule;
import com.acuo.collateral.modules.ServicesModule;
import com.acuo.collateral.modules.configuration.PropertiesHelper;
import com.acuo.collateral.services.*;
import com.acuo.common.app.ServiceManagerModule;
import com.acuo.common.util.GuiceJUnitRunner;
import com.acuo.common.util.GuiceJUnitRunner.GuiceModules;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.ServiceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.testutil.TestServer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules({ConfigurationIntegrationTestModule.class, ServicesModule.class, ServiceManagerModule.class, Neo4jIntegrationTestModule.class})
public class DataImporterIntegrationTest {

    private DataImporter importer;
    private CypherExecutor executor;

    @Inject
    ServiceManager serviceManager;

    @Inject
    Provider<TestServer> serverProvider;

    @Inject
    @Named(PropertiesHelper.ACUO_DATA_DIR)
    private String workingDirectory;

    @Inject
    @Named(PropertiesHelper.ACUO_CYPHER_DIR_TEMPLATE)
    private String directoryTemplate;

    @Inject
    DataLoader dataLoader;

    @Inject
    ClientService clientService;

    @Inject
    FundService fundService;

    @Inject
    PortfolioService portfolioService;

    @Inject
    ExposureService exposureService;

    @Inject
    CustodianService custodianService;

    @Inject
    CounterpartService counterpartService;

    @Inject
    ClearingHouseService clearingHouseService;

    @Before
    public void setup() {
        if (!serviceManager.isHealthy()) {
            serviceManager.startAsync().awaitHealthy();
        }
        importer = new Neo4jDataImporter(dataLoader, workingDirectory, workingDirectory, directoryTemplate);
        executor = new EmbeddedCypherExecutor(serverProvider.get().getGraphDatabaseService());
    }

    @Test
    public void testImportClients() {
        importer.importFiles("clients");

        Iterable<Client> clients = clientService.findAll();

        assertEquals(1, Iterables.size(clients));

        for (Client client : clients) {
            assertNotNull(client.getClientId());
            assertNotNull(client.getName());
        }
    }

    @Test
    public void testImportFunds() {
        importer.importFiles("clients", "funds");

        Iterable<Fund> funds = fundService.findAll();

        assertEquals(1, Iterables.size(funds));

        for (Fund fund : funds) {
            assertNotNull(fund.getFundId());
            assertNotNull(fund.getName());

            String query = "MATCH (client:Client)-[MANAGE]->(fund:Fund) WHERE fund.fundId = {fundId} RETURN client";
            ImmutableMap<String, Object> parameters = ImmutableMap.of("fundId", fund.getFundId());

            Collection<ICypherResult> results = executor.executeParameterisedStatement(query, parameters);
            assertNotNull(results);
            assertEquals(1, results.size());
        }
    }

    @Test
    public void testImportPortfolios() {
        importer.importFiles("clients", "funds", "portfolios");

        Iterable<Portfolio> portfolios = portfolioService.findAll();

        assertEquals(2, Iterables.size(portfolios));

        for (Portfolio portfolio : portfolios) {
            assertNotNull(portfolio.getPortfolioId());
            assertNotNull(portfolio.getName());
            assertNotNull(portfolio.getCurrency());

            String query = "MATCH (fund:Fund)-[IS_COMPOSED_OF]->(portfolio:Portfolio) WHERE portfolio.portfolioId = {portfolioId} RETURN fund";
            ImmutableMap<String, Object> parameters = ImmutableMap.of("portfolioId", portfolio.getPortfolioId());

            Collection<ICypherResult> results = executor.executeParameterisedStatement(query, parameters);
            assertNotNull(results);
            assertEquals(1, results.size());
        }
    }

    @Test
    public void testImportExposures() {
        importer.importFiles("clients", "ccps", "counterparts", "funds", "portfolios", "exposures");

        Iterable<Exposure> exposures = exposureService.findAll();

        assertEquals(8, Iterables.size(exposures));

        for (Exposure exposure : exposures) {
            assertNotNull(exposure.getPositionId());
            assertNotNull(exposure.getMaturityDate());
        }
    }

    @Test
    public void testImportCounterparts() {
        importer.importFiles("counterparts");

        Iterable<Counterpart> counterparts = counterpartService.findAll();

        assertEquals(4, Iterables.size(counterparts));

        for (Counterpart counterpart : counterparts) {
            assertNotNull(counterpart.getCounterpartId());
            assertNotNull(counterpart.getName());
        }
    }

    @Test
    public void testImportClearingHouses() {
        importer.importFiles("ccps");

        Iterable<ClearingHouse> clearingHouses = clearingHouseService.findAll();

        assertEquals(5, Iterables.size(clearingHouses));

        for (ClearingHouse clearingHouse : clearingHouses) {
            assertNotNull(clearingHouse.getClearingHouseId());
            assertNotNull(clearingHouse.getLei());
            assertNotNull(clearingHouse.getName());
        }
    }

    @Test
    public void testImportCustodians() {
        importer.importFiles("custodians");

        Iterable<Custodian> custodians = custodianService.findAll();

        assertEquals(4, Iterables.size(custodians));

        for (Custodian custodian : custodians) {
            assertNotNull(custodian.getCustodianId());
            assertNotNull(custodian.getName());
        }
    }

    @After
    public void teardown() {
        dataLoader.purgeDatabase();
    }
}
