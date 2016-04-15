package com.acuo.collateral.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.acuo.collateral.services.ImportService;
import com.google.inject.Inject;

@Path("/import")
public class ImportResource {

	private final ImportService service;

	@Inject
	public ImportResource(ImportService service) {
		this.service = service;
	}

	@GET
	@Path("reload")	
	public Response getByType(@PathParam("client_id") Long clientId) {
		service.reload();
		return Response.status(Status.OK).build();
	}
}
