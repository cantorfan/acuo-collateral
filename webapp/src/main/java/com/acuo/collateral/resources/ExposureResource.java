package com.acuo.collateral.resources;

import java.util.List;
import java.util.Map;
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

import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.resources.view.Counterpart;
import com.acuo.collateral.resources.view.Exposure;
import com.acuo.collateral.resources.view.Exposures;
import com.acuo.collateral.resources.view.ProductSet;
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
	@Path("/ByProductSetAndCount/{clientId}")
	@ResourceDetailView
	public Map<ProductSet, Integer> aggregateByProductSetAndCount(@PathParam("clientId") String clientId) {
		Exposures report = retrieveExposures(clientId);

		return report.classifyByProductSetAndCount();
	}

	@GET
	@Path("/ByCounterPartAndProductType/{clientId}")
	@ResourceDetailView
	public Map<Counterpart, Map<ProductType, Long>> aggregateByCounterPartAndProductType(
			@PathParam("clientId") String clientId) {

		Exposures report = retrieveExposures(clientId);

		return report.classifyByCounterPartAndProductType();
	}

	@GET
	@Path("/ByCounterPartAndProductSet/{clientId}")
	@ResourceDetailView
	public Map<Counterpart, Map<ProductSet, Long>> aggregateByCounterPartAndProductSet(
			@PathParam("clientId") String clientId) {
		Exposures report = retrieveExposures(clientId);
		return report.classifyByCounterPartAndProductSet();
	}

	private Exposures retrieveExposures(String clientId) {
		LOG.trace("Retrieving client Id: [{}]", clientId);

		Iterable<com.acuo.collateral.model.Exposure> exposures = exposureService.findByClientId(clientId);

		if (exposures == null) {
			throw new NotFoundException("Exposure not found.");
		}

		List<Exposure> exp = StreamSupport.stream(exposures.spliterator(), false).map(Exposure.createDetailed)
				.collect(Collectors.toList());

		return new Exposures(exp);
	}

}
