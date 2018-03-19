package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.service.MunicipioService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;

@Path("/municipio")
@RequestScoped
public class MunicipioRESTService extends GenericRESTService {
	
    @EJB
    private MunicipioService municipioService;

    @Inject
    ToJSON toJson;
    
    @GET
    @Path("{idProvincia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMunicipiosProvincia(@Context HttpServletRequest req,@PathParam("idProvincia") Long idProvincia) {
    	if (estaUsuarioLogeado(req)) {
    		return buildResponse(municipioService.obtenerMunicipiosProvincia(idProvincia));	
    	}
    	else {
    		throw new WebApplicationException("No ha iniciado sesión.");
    	}
       
    }

}
