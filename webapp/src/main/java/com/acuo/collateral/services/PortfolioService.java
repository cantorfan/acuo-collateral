package com.acuo.collateral.services;

import com.acuo.collateral.model.Portfolio;
import org.neo4j.ogm.annotation.Transient;

@Transient
public interface PortfolioService extends Service<Portfolio> {

    public Iterable<Portfolio> findByClientId(Long id);
}
