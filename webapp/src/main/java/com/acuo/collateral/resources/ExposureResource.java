package com.acuo.collateral.resources;

import com.acuo.collateral.model.ProductType;
import com.acuo.collateral.resources.view.Counterpart;
import com.acuo.collateral.resources.view.Exposure;
import com.acuo.collateral.resources.view.Exposures;
import com.acuo.collateral.resources.view.ProductSet;
import com.acuo.collateral.services.ExposureService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    @Timed
    public Iterable<Exposure> findAll() {
        LOG.trace("Retrieving all exposures");

        Iterable<com.acuo.collateral.model.Exposure> exposures = exposureService.findAll();

        return StreamSupport.stream(exposures.spliterator(), false).map(Exposure.create).collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Timed
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
    @Timed
    public Map<ProductSet, Integer> aggregateByProductSetAndCount(@PathParam("clientId") String clientId) {
        Exposures report = retrieveExposures(clientId);

        return report.classifyByProductSetAndCount();
    }

    @GET
    @Path("/ByCounterPartAndProductType/{clientId}")
    @Timed
    public Map<Counterpart, Map<ProductType, Long>> aggregateByCounterPartAndProductType(
            @PathParam("clientId") String clientId) {

        Exposures report = retrieveExposures(clientId);

        return report.classifyByCounterPartAndProductType();
    }

    @GET
    @Path("/ByCounterPartAndProductSet/{clientId}")
    @Timed
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