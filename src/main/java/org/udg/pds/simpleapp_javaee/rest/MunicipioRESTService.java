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

import org.udg.pds.simpleapp_javaee.service.MunicipioService;
import org.udg.pds.simpleapp_javaee.service.ProvinciaService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;

@Path("/municipios")
@RequestScoped
public class MunicipioRESTService extends RESTService {
	
    @EJB
    private MunicipioService municipioService;

    @Inject
    ToJSON toJson;
    
    @GET
    @Path("{idProvincia}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMunicipiosProvincia(@PathParam("idProvincia") Long idProvincia) {
       return buildResponse(municipioService.obtenerMunicipiosProvincia(idProvincia));
    }

}
