package com.acuo.collateral.model;

import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Fund extends Entity {

	private String name;

	public String getName() {
		return name;
	}

	@Relationship(type = "IS_COMPOSED_OF")
	private Set<Portfolio> portfolios;

	public Set<Portfolio> getPortfolios() {
		return portfolios;
	}
}