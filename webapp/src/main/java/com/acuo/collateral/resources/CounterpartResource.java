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

import com.acuo.collateral.services.CounterpartService;
import com.google.inject.persist.Transactional;

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
	public Iterable<Counterpart> findAll() {
		LOG.trace("Retrieving all counterparts");

		Iterable<com.acuo.collateral.model.Counterpart> exposures = counterpartService.findAll();

		return StreamSupport.stream(exposures.spliterator(), false).map(Counterpart.create)
				.collect(Collectors.toList());
	}

	@GET
	@Path("{id}")
	@ResourceDetailView
	public Counterpart findById(@PathParam("id") Long id) {
		LOG.trace("Retrieving Class with ID: [{}]", id);

		com.acuo.collateral.model.Counterpart counterpart = counterpartService.find(id);

		if (counterpart == null) {
			throw new NotFoundException("Exposure not found.");
		}

		return Counterpart.createDetailed.apply(counterpart);
	}

	public static class Counterpart {

		public String counterpartId;
		public String name;

		public static Function<com.acuo.collateral.model.Counterpart, Counterpart> create = t -> {
			Counterpart counterpart = new Counterpart();
			counterpart.counterpartId = t.getCounterpartId();
			counterpart.name = t.getName();
			return counterpart;
		};

		public static Function<com.acuo.collateral.model.Counterpart, Counterpart> createDetailed = t -> {
			return Counterpart.create.apply(t);
		};

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((counterpartId == null) ? 0 : counterpartId.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Counterpart other = (Counterpart) obj;
			if (counterpartId == null) {
				if (other.counterpartId != null)
					return false;
			} else if (!counterpartId.equals(other.counterpartId))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Counterpart [counterpartId=" + counterpartId + ", name=" + name + "]";
		}

	}

}
