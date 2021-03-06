package com.acuo.collateral.persist;

import static com.acuo.common.TestHelper.matchesArgRegex;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DataImporterTest {

	private DataImporter importer;

	@Mock
	DataLoader loader;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		importer = new Neo4jDataImporter(loader, "graph-data", "", "%s/cypher/%s.load");
	}

	@Test
	public void testCreateClient() {
		String query = "^LOAD CSV WITH HEADERS FROM.*AS clientLine.*";

		importer.importFiles("clients");

		verify(loader).loadData(argThat(matchesArgRegex(query)));
	}
}
