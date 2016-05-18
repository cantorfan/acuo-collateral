package com.acuo.collateral.persist;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.session.Session;

import com.acuo.collateral.modules.PropertiesHelper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.persist.Transactional;

public class SessionDataLoader implements DataLoader {

	public static final String LOAD_ALL_CQL = "data.cql";
	public static final String DELETE_ALL_CQL = "deleteAll.cql";
	public static final String CONSTRAINTS_CQL = "constraints.cql";

	private final Session session;
	private final CypherFileSpliter spliter;

	@Inject
	public SessionDataLoader(Session session, @Named(PropertiesHelper.ACUO_DATA_DIR) String dataDirectory) {
		this.session = session;
		this.spliter = CypherFileSpliter.of(dataDirectory);
	}

	@Transactional
	@Override
	public void purgeDatabase() {
		session.purgeDatabase();
	}

	@Override
	public void loadAll() {
		loadDataFile(LOAD_ALL_CQL);
	}

	@Override
	public void createConstraints() {
		loadDataFile(CONSTRAINTS_CQL);
	}

	@Override
	public void loadDataFile(String fileName) {
		List<String> lines = spliter.splitByDefaultDelimiter(fileName);
		for (String query : lines) {
			loadData(query);
		}
	}

	@Transactional
	@Override
	public void loadData(String query) {
		if (StringUtils.isEmpty(query))
			return;
		session.query(query, Collections.emptyMap());
	}
}
