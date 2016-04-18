package com.acuo.collateral.services;

import java.util.Collections;

import com.acuo.collateral.model.Portfolio;

public class PortfolioServiceImpl extends GenericService<Portfolio> implements PortfolioService {

	@Override
	public Class<Portfolio> getEntityType() {
		return Portfolio.class;
	}
	
	@Override
	public Iterable<Portfolio> findByClientId(Long id) {
		Iterable<Portfolio> portfolios = session.query(Portfolio.class, 
														"MATCH (client:Client) -[*]- (p:Portfolio) WHERE ID(client)={param} WITH p MATCH a=(p)-[*0..1]-(b) RETURN a",
														Collections.<String, Object>singletonMap("param", id));
		return portfolios;
	}

}
