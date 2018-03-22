package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.model.Ubicacion;
import org.udg.pds.simpleapp_javaee.service.UbicacionService;
import request.RequestUbicacion.RequestUbicacionUsuario;

@Path("/ubicacion")
@RequestScoped
public class UbicacionRESTService extends GenericRESTService {

	@EJB
	UbicacionService ubicacionService;

	@POST
	@Path("usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response guardarUbicacionActualUsuario(@Context HttpServletRequest req,
			@Valid RequestUbicacionUsuario datosUbicacion) {

		if (estaUsuarioLogeado(req)) {
			Ubicacion ubicacion = ubicacionService.guardarUbicacionGPS(datosUbicacion, obtenerUsuarioLogeado(req));
			return buildResponse(new ResponseGenericId(ubicacion.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

}
