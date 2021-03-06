package com.acuo.collateral.resources;

import com.google.inject.persist.Transactional;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.function.Function;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional(ignore = WebApplicationException.class)
public class CategoryResource {

    public static class Category {

        public Long id;
        public String type;

        public static Function<com.acuo.collateral.model.Category, Category> create = t -> {
            Category category = new Category();
            category.id = t.getId();
            category.type = t.getType();

            return category;
        };
    }

}
