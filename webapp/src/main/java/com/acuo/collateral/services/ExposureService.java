package com.acuo.collateral.services;

import org.neo4j.ogm.annotation.Transient;

import com.acuo.collateral.model.Exposure;

@Transient
public interface ExposureService extends Service<Exposure> {

	Iterable<Exposure> findByClientId(String clientId);

}
