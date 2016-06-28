package com.acuo.collateral.resources;

import com.acuo.collateral.resources.view.Exposure;
import com.acuo.collateral.services.PortfolioService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    @Timed
    public Iterable<Portfolio> findAll() {
        LOG.trace("Retrieving all clients");

        Iterable<com.acuo.collateral.model.Portfolio> portfolios = portfolioService.findAll();

        return StreamSupport.stream(portfolios.spliterator(), false).map(Portfolio.create).collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @ResourceDetailView
    @Timed
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
    @Timed
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
            portfolio.exposures = t.getExposures().stream().map(Exposure.create).collect(Collectors.toSet());
            portfolio.assets = t.getAssets().stream().map(AssetResource.Asset.create).collect(Collectors.toSet());

            return portfolio;
        };

        public Long id;

        public String name;

        public String currency;

        @ResourceDetailView
        public Set<Exposure> exposures;

        @ResourceDetailView
        public Set<AssetResource.Asset> assets;

    }
}