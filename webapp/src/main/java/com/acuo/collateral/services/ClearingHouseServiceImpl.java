package com.acuo.collateral.services;

import com.acuo.collateral.model.ClearingHouse;

public class ClearingHouseServiceImpl extends GenericService<ClearingHouse> implements ClearingHouseService {

	@Override
	public Class<ClearingHouse> getEntityType() {
		return ClearingHouse.class;
	}

}
