package com.acuo.collateral.services;

import com.acuo.collateral.model.Custodian;

public class CustodianServiceImpl extends GenericService<Custodian> implements CustodianService {

    @Override
    public Class<Custodian> getEntityType() {
        return Custodian.class;
    }
}
