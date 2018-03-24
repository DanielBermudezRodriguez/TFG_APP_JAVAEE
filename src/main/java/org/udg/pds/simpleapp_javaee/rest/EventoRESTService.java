package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.service.EventoService;
import request.RequestEvento.RequestCrearEvento;

@Path("/evento")
@RequestScoped
public class EventoRESTService extends GenericRESTService {

	@EJB
	EventoService eventoService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearEventoDeportivo(@Context HttpServletRequest req, @Valid RequestCrearEvento datosEvento) {

		if (estaUsuarioLogeado(req)) {
			Long idUsuario = obtenerUsuarioLogeado(req);
			Evento e = eventoService.crearEventoDeportivo(datosEvento, idUsuario);
			return buildResponse(new ResponseGenericId(e.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarEvento(@Context HttpServletRequest req, @Valid RequestGenericId idEvento) {
		if (estaUsuarioLogeado(req)) {
			Evento e = eventoService.cancelarEvento(obtenerUsuarioLogeado(req), idEvento.id);
			return buildResponse(new ResponseGenericId(e.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

}
