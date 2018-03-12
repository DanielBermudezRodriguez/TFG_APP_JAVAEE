package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.udg.pds.simpleapp_javaee.service.PaisService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;


@Path("/paises")
@RequestScoped
public class PaisRESTService extends RESTService {
	
    @EJB
    private PaisService paisService;

    @Inject
    ToJSON toJson;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPaises() {
       return buildResponse(paisService.obtenerPaises());
    }

}
