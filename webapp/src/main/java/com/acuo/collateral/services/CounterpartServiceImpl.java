package com.acuo.collateral.services;

import com.acuo.collateral.model.Counterpart;

public class CounterpartServiceImpl extends GenericService<Counterpart> implements CounterpartService {

    @Override
    public Class<Counterpart> getEntityType() {
        return Counterpart.class;
    }

}
