package com.acuo.collateral.services;

import com.acuo.collateral.model.Client;

public class ClientServiceImpl extends GenericService<Client> implements ClientService {

	@Override
	public Class<Client> getEntityType() {
		return Client.class;
	}
	
}
