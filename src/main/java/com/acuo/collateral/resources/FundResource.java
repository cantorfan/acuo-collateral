package com.acuo.collateral.resources;

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

import com.acuo.collateral.services.FundService;
import com.google.inject.persist.Transactional;

@Path("/funds")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class FundResource {

	private static final Logger LOG = LoggerFactory.getLogger(FundResource.class);

	private final FundService fundService;

	@Inject
	public FundResource(FundService fundService) {
		this.fundService = fundService;
	}

	@GET
	public Iterable<Fund> findAll() {
		LOG.trace("Retrieving all clients");

		Iterable<com.acuo.collateral.model.Fund> funds = fundService.findAll();

		return StreamSupport.stream(funds.spliterator(), false).map(Fund.create).collect(Collectors.toList());
	}

	@GET
	@Path("{id}")
	@ResourceDetailView
	public Fund getByType(@PathParam("id") Long id) {
		LOG.trace("Retrieving fund with ID: [{}]", id);

		com.acuo.collateral.model.Fund fund = fundService.find(id);

		if (fund == null) {
			throw new NotFoundException("Fund not found.");
		}

		return Fund.createDetailed.apply(fund);
	}

	public static class Fund {
		public static Function<com.acuo.collateral.model.Fund, Fund> create = t -> {
			Fund fund = new Fund();
			fund.id = t.getId();
			fund.name = t.getName();
			return fund;
		};

		public static Function<com.acuo.collateral.model.Fund, Fund> createDetailed = t -> {
			Fund fund = new Fund();
			fund.id = t.getId();
			fund.name = t.getName();
			return fund;
		};

		public Long id;

		public String name;
	}
}
