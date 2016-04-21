package com.acuo.collateral.services;

import com.acuo.collateral.model.Fund;

public class FundServiceImpl extends GenericService<Fund> implements FundService {

	@Override
	public Class<Fund> getEntityType() {
		return Fund.class;
	}
	
}
