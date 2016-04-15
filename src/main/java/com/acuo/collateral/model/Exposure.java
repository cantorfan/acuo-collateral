package com.acuo.collateral.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Exposure extends Entity {

	private String key;
	
	private String type;
	
	public String getKey() {
		return key;
	}
	
	public String getType() {
		return type;
	}
}
