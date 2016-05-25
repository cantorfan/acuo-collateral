package com.acuo.collateral.services;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;

import com.acuo.collateral.model.Exposure;
import com.google.common.collect.ImmutableMap;
import com.google.inject.persist.Transactional;

public class ExposureServiceImpl extends GenericService<Exposure> implements ExposureService {

	@Inject
	Session session;

	@Override
	public Class<Exposure> getEntityType() {
		return Exposure.class;
	}

	@Transactional
	@Override
	public Iterable<Exposure> findByClientId(String clientId) {
		String query = "MATCH (client:Client {clientId:{clientId}})-[*]-(exposure:Exposure)-[w:IS_DEALT_WITH]->(counterpart:Counterpart) return exposure, w, counterpart";
		Iterable<Exposure> results = session.query(Exposure.class, query, ImmutableMap.of("clientId", clientId));
		return results;
	}

}
