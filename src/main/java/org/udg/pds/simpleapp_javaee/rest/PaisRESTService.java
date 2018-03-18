package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.service.PaisService;

@Path("/pais")
@RequestScoped
public class PaisRESTService extends GenericRESTService {

	@EJB
	private PaisService paisService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerPaises(@Context HttpServletRequest req) {

		if (estaUsuarioLogeado(req)) {
			return buildResponse(paisService.obtenerPaises());
		} else {
			throw new WebApplicationException("No ha iniciado sesión.");
		}
	}

}
