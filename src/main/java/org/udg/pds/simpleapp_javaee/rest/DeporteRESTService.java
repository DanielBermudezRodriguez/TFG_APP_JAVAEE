package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.service.DeporteService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;

@Path("/deporte")
@RequestScoped
public class DeporteRESTService extends GenericRESTService {

	@EJB
	private DeporteService deporteService;

	@Inject
	ToJSON toJson;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerDeportes(@Context HttpServletRequest req) {
		return buildResponse(deporteService.obtenerDeportes());
	}

}
