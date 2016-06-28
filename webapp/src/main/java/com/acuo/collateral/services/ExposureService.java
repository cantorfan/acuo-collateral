package com.acuo.collateral.services;

import com.acuo.collateral.model.Exposure;
import org.neo4j.ogm.annotation.Transient;

@Transient
public interface ExposureService extends Service<Exposure> {

    Iterable<Exposure> findByClientId(String clientId);

}
