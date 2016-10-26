package com.acuo.collateral.modules.persistence;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;

public class PersistServiceInitializer {

    @Inject
    PersistServiceInitializer(PersistService service) {
        service.start();
    } 
}