package com.acuo.collateral.neo4j.utils;

import org.neo4j.graphdb.GraphDatabaseService;

import com.graphaware.test.unit.GraphUnit;

public class GraphTestHelper {

	public static void assertEmpty(GraphDatabaseService database) {
		GraphUnit.assertEmpty(database);
	}
}
