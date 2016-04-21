package com.acuo.collateral.services;

import org.neo4j.ogm.annotation.Transient;

import com.acuo.collateral.model.Portfolio;

@Transient
public interface PortfolioService extends Service<Portfolio> {

	public Iterable<Portfolio> findByClientId(Long id);
}
