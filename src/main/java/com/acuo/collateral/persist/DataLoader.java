package com.acuo.collateral.persist;

import org.neo4j.ogm.annotation.Transient;

@Transient
public interface DataLoader {

	void purgeDatabase();

	void createConstraints();

	void loadData(String query);

}