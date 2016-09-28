package com.acuo.collateral.resources;

import com.acuo.collateral.resources.view.Counterpart;
import com.acuo.collateral.services.CounterpartService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Path("/counterpart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class CounterpartResource {

    private static final Logger LOG = LoggerFactory.getLogger(CounterpartResource.class);

    private final CounterpartService counterpartService;

    @Inject
    public CounterpartResource(CounterpartService counterpartService) {
        this.counterpartService = counterpartService;
    }

    @GET
    @Timed
    public Iterable<Counterpart> findAll() {
        LOG.trace("Retrieving all counterparts");

        Iterable<com.acuo.collateral.model.Counterpart> exposures = counterpartService.findAll();

        return StreamSupport.stream(exposures.spliterator(), false).map(Counterpart.create)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Timed
    public Counterpart findById(@PathParam("id") Long id) {
        LOG.trace("Retrieving Class with ID: [{}]", id);

        com.acuo.collateral.model.Counterpart counterpart = counterpartService.find(id);

        if (counterpart == null) {
            throw new NotFoundException("Exposure not found.");
        }

        return Counterpart.createDetailed.apply(counterpart);
    }
}