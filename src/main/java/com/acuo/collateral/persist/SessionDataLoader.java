package com.acuo.collateral.persist;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.session.Session;

import com.google.inject.Inject;

public class SessionDataLoader implements DataLoader {

	public static final String DELETE_ALL_CQL = "deleteAll.cql";
	public static final String CONSTRAINTS_CQL = "constraints.cql";
	
	private final Session session;
	private final CypherFileSpliter spliter;

	@Inject
	public SessionDataLoader(Session session) {
		this.session = session;
		this.spliter = CypherFileSpliter.of("/");
	}

	@Override
	public void purgeDatabase() {
		session.purgeDatabase();
	}

	@Override
	public void createConstraints() {
		loadDataFile(CONSTRAINTS_CQL);
	}

	public void loadDataFile(String fileName) {
		List<String> lines = spliter.splitByDefaultDelimiter(fileName);
		for (String query : lines) {
			loadData(query);
		}
	}

	@Override
	public void loadData(String query) {
		if (StringUtils.isEmpty(query))
			return;
		session.query(query, Collections.emptyMap());
	}
}
