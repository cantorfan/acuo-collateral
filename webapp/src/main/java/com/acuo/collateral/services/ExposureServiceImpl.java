package com.acuo.collateral.services;

import com.acuo.collateral.model.Exposure;

public class ExposureServiceImpl extends GenericService<Exposure> implements ExposureService {

	@Override
	public Class<Exposure> getEntityType() {
		return Exposure.class;
	}

}
