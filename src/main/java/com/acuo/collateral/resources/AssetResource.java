package com.acuo.collateral.resources;

import java.util.function.Function;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.google.inject.persist.Transactional;

@Path("/assets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class AssetResource {

	public static class Asset {

		public Long id;
		public String key;
		public CategoryResource.Category category;
		
		public static Function<com.acuo.collateral.model.Asset, Asset> create = t -> {
			Asset asset = new Asset();
			asset.id = t.getId();
			asset.key = t.getKey();
			if (t.getCategory() != null)
				asset.category = CategoryResource.Category.create.apply(t.getCategory());

			return asset;
		};

	}

}
