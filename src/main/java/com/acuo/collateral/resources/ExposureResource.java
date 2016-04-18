package com.acuo.collateral.resources;

import java.util.function.Function;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.google.inject.persist.Transactional;

@Path("/exposures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class ExposureResource {

	public static class Exposure {

		public Long id;
		public String key;
		public String type;
		
		public static Function<com.acuo.collateral.model.Exposure, Exposure> create = t -> {
			Exposure exposure = new Exposure();
			exposure.id = t.getId();
			exposure.key = t.getKey();
			exposure.type = t.getType();

			return exposure;
		};

	}

}
