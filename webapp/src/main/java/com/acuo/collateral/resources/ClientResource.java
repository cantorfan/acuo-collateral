package com.acuo.collateral.resources;

import com.acuo.collateral.services.ClientService;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class ClientResource {

    private static final Logger LOG = LoggerFactory.getLogger(ClientResource.class);

    private final ClientService clientService;

    @Inject
    public ClientResource(ClientService clientService) {
        this.clientService = clientService;
    }

    @GET
    @Timed
    public Iterable<Client> findAll() {
        LOG.trace("Retrieving all clients");

        Iterable<com.acuo.collateral.model.Client> clients = clientService.findAll();

        return StreamSupport.stream(clients.spliterator(), false).map(Client.create).collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @Timed
    public Client findById(@PathParam("id") Long id) {
        LOG.trace("Retrieving Class with ID: [{}]", id);

        com.acuo.collateral.model.Client client = clientService.find(id);

        if (client == null) {
            throw new NotFoundException("Client not found.");
        }

        return Client.createDetailed.apply(client);
    }

    public static class Client {
        public static Function<com.acuo.collateral.model.Client, Client> create = t -> {
            Client client = new Client();
            client.id = t.getId();
            client.name = t.getName();

            return client;
        };

        public static Function<com.acuo.collateral.model.Client, Client> createDetailed = t -> {
            Client client = new Client();
            client.id = t.getId();
            client.name = t.getName();
            // client.subject = Subject.create.apply(t.getSubject());
            client.funds = t.getFunds().stream().map(FundResource.Fund.create).collect(Collectors.toSet());

            return client;
        };

        public Long id;

        public String name;

        // @ResourceDetailView
        // public Subject subject;

        public Set<FundResource.Fund> funds;

    }
}