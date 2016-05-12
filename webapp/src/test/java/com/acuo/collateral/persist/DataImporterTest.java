package com.acuo.collateral.persist;

import static com.acuo.common.TestHelper.matchesRegex;
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

		importer = new DataImporter(loader, "graph-data", "%s/cypher/%s.load");
	}

	@Test
	public void testCreateClient() {
		String query = "^LOAD CSV WITH HEADERS FROM.*AS clientLine.*";

		importer.importFile("clients");

		verify(loader).loadData(argThat(matchesRegex(query)));
	}
}
