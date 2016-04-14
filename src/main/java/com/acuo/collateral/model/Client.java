package com.acuo.collateral.model;

import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Client extends Entity {
    
    @GraphId
	private Long id;

    private String name;
    
    public Client() {
    }

    public Client(String name) {
        this.name = name;
    }
    
    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    	
    @Relationship(type = "MANAGE")
    private Set<Fund> funds;
    
    public Set<Fund> getFunds()
    {
        return funds;
    }    
}
