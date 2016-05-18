package com.acuo.collateral.resources;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acuo.collateral.model.Direction;
import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.model.Status;
import com.acuo.collateral.resources.CounterpartResource.Counterpart;
import com.acuo.collateral.services.ExposureService;
import com.google.inject.persist.Transactional;

@Path("/exposures")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class ExposureResource {

	private static final Logger LOG = LoggerFactory.getLogger(ExposureResource.class);

	private final ExposureService exposureService;

	@Inject
	public ExposureResource(ExposureService exposureService) {
		this.exposureService = exposureService;
	}

	@GET
	public Iterable<Exposure> findAll() {
		LOG.trace("Retrieving all exposures");

		Iterable<com.acuo.collateral.model.Exposure> exposures = exposureService.findAll();

		return StreamSupport.stream(exposures.spliterator(), false).map(Exposure.create).collect(Collectors.toList());
	}

	@GET
	@Path("{id}")
	@ResourceDetailView
	public Exposure findById(@PathParam("id") Long id) {
		LOG.trace("Retrieving Class with ID: [{}]", id);

		com.acuo.collateral.model.Exposure exposure = exposureService.find(id);

		if (exposure == null) {
			throw new NotFoundException("Exposure not found.");
		}

		return Exposure.createDetailed.apply(exposure);
	}

	@GET
	@Path("/client/{clientId}")
	@ResourceDetailView
	public Map<Counterpart, List<Exposure>> findByClientId(@PathParam("clientId") String clientId) {
		LOG.trace("Retrieving client Id: [{}]", clientId);

		Iterable<com.acuo.collateral.model.Exposure> exposures = exposureService.findByClientId(clientId);

		if (exposures == null) {
			throw new NotFoundException("Exposure not found.");
		}

		Map<Counterpart, List<Exposure>> map = StreamSupport.stream(exposures.spliterator(), false)
				.map(Exposure.createDetailed).collect(Collectors.groupingBy(a -> a.counterpart));
		return map;
	}

	public static class Exposure {

		public String positionId;
		public String note;
		public ProductType productType;
		public Date tradeDate;
		public Date effectiveDate;
		public Date maturityDate;
		public Date clearingDate;
		public Direction direction;
		public Status status;
		public String source;
		public Counterpart counterpart;

		public static Function<com.acuo.collateral.model.Exposure, Exposure> create = t -> {
			Exposure exposure = new Exposure();
			exposure.positionId = t.getPositionId();
			exposure.note = t.getNote();
			exposure.productType = t.getProductType();
			exposure.tradeDate = t.getTradeDate();
			exposure.effectiveDate = t.getEffectiveDate();
			exposure.maturityDate = t.getMaturityDate();
			exposure.clearingDate = t.getClearingDate();
			exposure.direction = t.getDirection();
			exposure.status = t.getStatus();
			exposure.source = t.getSource();

			return exposure;
		};

		public static Function<com.acuo.collateral.model.Exposure, Exposure> createDetailed = t -> {
			Exposure exposure = Exposure.create.apply(t);
			exposure.counterpart = Counterpart.create.apply(t.getCounterpart());

			return exposure;
		};

	}

}
