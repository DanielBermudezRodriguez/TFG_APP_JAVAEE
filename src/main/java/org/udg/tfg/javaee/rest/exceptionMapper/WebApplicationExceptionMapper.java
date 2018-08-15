package org.udg.tfg.javaee.rest.exceptionMapper;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.udg.tfg.javaee.util.ToJSON;


@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<javax.ws.rs.WebApplicationException> {
  @Inject ToJSON toJSON;

  @Override
  public Response toResponse(javax.ws.rs.WebApplicationException e) {
    return Response.serverError().entity(toJSON.buildError( e.getMessage())).type(MediaType.APPLICATION_JSON_TYPE).build();
  }
}

