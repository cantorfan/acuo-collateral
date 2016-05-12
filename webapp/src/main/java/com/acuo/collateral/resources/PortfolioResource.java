package com.acuo.collateral.resources;

import java.util.List;
import java.util.Set;
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

import com.acuo.collateral.services.PortfolioService;
import com.google.inject.persist.Transactional;

@Path("/portfolios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class PortfolioResource {

	private static final Logger LOG = LoggerFactory.getLogger(PortfolioResource.class);

	private final PortfolioService portfolioService;

	@Inject
	public PortfolioResource(PortfolioService portfolioService) {
		this.portfolioService = portfolioService;
	}

	@GET
	public Iterable<Portfolio> findAll() {
		LOG.trace("Retrieving all clients");

		Iterable<com.acuo.collateral.model.Portfolio> portfolios = portfolioService.findAll();

		return StreamSupport.stream(portfolios.spliterator(), false).map(Portfolio.create).collect(Collectors.toList());
	}

	@GET
	@Path("{id}")
	@ResourceDetailView
	public Portfolio findById(@PathParam("id") Long id) {
		LOG.trace("Retrieving Class with ID: [{}]", id);

		com.acuo.collateral.model.Portfolio client = portfolioService.find(id);

		if (client == null) {
			throw new NotFoundException("Client not found.");
		}

		return Portfolio.createDetailed.apply(client);
	}

	@GET
	@Path("client={clientId}")
	@ResourceDetailView
	public Iterable<Portfolio> findByClientId(@PathParam("clientId") Long clientId) {
		LOG.trace("Retrieving Class with client ID: [{}]", clientId);

		Iterable<com.acuo.collateral.model.Portfolio> portfolios = portfolioService.findByClientId(clientId);

		if (portfolios == null) {
			throw new NotFoundException("Portfolio not found.");
		}

		List<Portfolio> c = StreamSupport.stream(portfolios.spliterator(), false).map(Portfolio.createDetailed)
				.collect(Collectors.toList());
		return c;
	}

	public static class Portfolio {

		public static Function<com.acuo.collateral.model.Portfolio, Portfolio> create = t -> {
			Portfolio portfolio = new Portfolio();
			portfolio.id = t.getId();
			portfolio.currency = t.getCurrency();
			portfolio.name = t.getName();

			return portfolio;
		};

		public static Function<com.acuo.collateral.model.Portfolio, Portfolio> createDetailed = t -> {
			Portfolio portfolio = new Portfolio();
			portfolio.id = t.getId();
			portfolio.name = t.getName();
			portfolio.currency = t.getCurrency();
			portfolio.exposures = t.getExposures().stream().map(ExposureResource.Exposure.create)
					.collect(Collectors.toSet());
			portfolio.assets = t.getAssets().stream().map(AssetResource.Asset.create).collect(Collectors.toSet());

			return portfolio;
		};

		public Long id;

		public String name;

		public String currency;

		@ResourceDetailView
		public Set<ExposureResource.Exposure> exposures;

		@ResourceDetailView
		public Set<AssetResource.Asset> assets;

	}
}