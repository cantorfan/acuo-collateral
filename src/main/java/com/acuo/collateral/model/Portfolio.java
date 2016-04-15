package com.acuo.collateral.model;

import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Portfolio extends Entity {

	private String name;

	public String getName() {
		return name;
	}
	
	@Relationship(type = "IS_COMPOSED_OF")
	private Set<Exposure> exposures;

	public Set<Exposure> getExposures() {
		return exposures;
	}
	
	@Relationship(type = "IS_COMPOSED_OF")
	private Set<Asset> assets;

	public Set<Asset> getAssets() {
		return assets;
	}
}
