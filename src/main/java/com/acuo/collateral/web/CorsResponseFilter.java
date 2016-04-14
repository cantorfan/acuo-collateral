package com.acuo.collateral.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class CorsResponseFilter implements ContainerResponseFilter
{
    private static final Logger LOG = LoggerFactory.getLogger(CorsResponseFilter.class);

    private static final String ALLOWED_METHODS = "GET,POST,DELETE,PUT,OPTIONS,HEAD";

    private final static String DEFAULT_ALLOWED_HEADERS = "Content-Type,X-Requested-With,Accept,Authorization";

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException
    {

//        List<String> origins = requestContext.getHeaders().get("Origin");
//
//        if (origins == null || origins.size() != 1)
//        {
//            throw new ForbiddenException("Requests from this Origin not allowed.");
//        }
//
//        String origin = origins.get(0);
//
//        LOG.info("Request made from: [{}]", origin);

        final MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Allow-Headers", DEFAULT_ALLOWED_HEADERS);
    }

}
