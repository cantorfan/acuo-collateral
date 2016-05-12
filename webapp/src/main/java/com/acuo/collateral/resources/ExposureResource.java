package com.acuo.collateral.resources;

import java.util.Date;
import java.util.function.Function;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import com.acuo.collateral.model.Direction;
import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.model.Status;
import com.google.inject.persist.Transactional;

@Path("/exposures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class ExposureResource {

	public static class Exposure {

		private String positionId;
		private String note;
		private ProductType productType;
		private Date tradeDate;
		private Date effectiveDate;
		private Date maturityDate;
		private Date clearingDate;
		private Direction direction;
		private Status status;
		private String source;

		public static Function<com.acuo.collateral.model.Exposure, Exposure> create = t -> {
			Exposure exposure = new Exposure();
			exposure.positionId = t.getPositionId();
			exposure.direction = t.getDirection();
			exposure.tradeDate = t.getTradeDate();

			return exposure;
		};

	}

}
