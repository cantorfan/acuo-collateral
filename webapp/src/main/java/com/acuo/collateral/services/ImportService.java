package com.acuo.collateral.services;

import org.neo4j.ogm.annotation.Transient;

@Transient
public interface ImportService {

    void reload();

}