package com.acuo.collateral.model;

import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Client extends Entity {

	private String name;

	public String getName() {
		return name;
	}

	@Relationship(type = "MANAGE")
	private Set<Fund> funds;

	public Set<Fund> getFunds() {
		return funds;
	}
}
