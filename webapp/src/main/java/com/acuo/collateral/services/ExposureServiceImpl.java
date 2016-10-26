package com.acuo.collateral.services;

import com.acuo.collateral.model.Exposure;
import com.google.common.collect.ImmutableMap;
import com.google.inject.persist.Transactional;

public class ExposureServiceImpl extends GenericService<Exposure> implements ExposureService {

    @Override
    public Class<Exposure> getEntityType() {
        return Exposure.class;
    }

    @Transactional
    @Override
    public Iterable<Exposure> findByClientId(String clientId) {
        String query = "MATCH (client:Client {clientId:{clientId}})-[*]-(exposure:Exposure)-[w:IS_DEALT_WITH]->(counterpart:Counterpart) return exposure, w, counterpart";
        Iterable<Exposure> results = sessionProvider.get().query(Exposure.class, query, ImmutableMap.of("clientId", clientId));
        return results;
    }

}
