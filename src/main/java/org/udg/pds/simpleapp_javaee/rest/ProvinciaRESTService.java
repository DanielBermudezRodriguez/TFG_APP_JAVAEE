package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.udg.pds.simpleapp_javaee.service.PaisService;
import org.udg.pds.simpleapp_javaee.service.ProvinciaService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;

@Path("/provincias")
@RequestScoped
public class ProvinciaRESTService extends RESTService{
	
    @EJB
    private ProvinciaService provinciaService;

    @Inject
    ToJSON toJson;
    
    @GET
    @Path("{idPais}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerProvinciasPais(@PathParam("idPais") Long idPais) {
       return buildResponse(provinciaService.obtenerProvinciasPais(idPais));
    }

}
