package org.udg.tfg.javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.udg.tfg.javaee.model.Ubicacion;
import org.udg.tfg.javaee.request.RequestUbicacion.RequestUbicacionUsuario;
import org.udg.tfg.javaee.response.ResponseUbicacion;
import org.udg.tfg.javaee.service.UbicacionService;

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

	@GET
	@Path("{idEvento}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ubicacionEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento) {

		if (estaUsuarioLogeado(req)) {
			ResponseUbicacion.ResponseUbicacionEvento ubicacion = ubicacionService.ubicacionEvento(idEvento);
			return buildResponse(ubicacion);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

}
