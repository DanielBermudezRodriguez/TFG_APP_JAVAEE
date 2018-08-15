package org.udg.tfg.javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.udg.tfg.javaee.model.Notificacion;
import org.udg.tfg.javaee.request.RequestNotificacion.RequestModificarNotificacion;
import org.udg.tfg.javaee.service.NotificacionService;

@Path("/notificacion")
@RequestScoped
public class NotificacionRESTService extends GenericRESTService {

	@EJB
	private NotificacionService notificacionService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response configuracionNotificacionesUsuario(@Context HttpServletRequest req) {
		// Obtener configuracion notificaciones push del usuario logeado
		if (estaUsuarioLogeado(req)) {
			Notificacion notificaciones = notificacionService
					.obtenerConfiguracionNotificaciones(obtenerUsuarioLogeado(req));
			return buildResponse(notificaciones);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarConfiguracionNotificaciones(@Context HttpServletRequest req,
			@Valid RequestModificarNotificacion datosNotificaciones) {

		if (estaUsuarioLogeado(req)) {
			Notificacion notificaciones = notificacionService.modificarNotificaciones(obtenerUsuarioLogeado(req),
					datosNotificaciones);
			return buildResponse(notificaciones);
		}
		throw new WebApplicationException("No ha iniciado sesión");

	}

}
