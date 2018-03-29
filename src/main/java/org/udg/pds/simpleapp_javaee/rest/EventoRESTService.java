package org.udg.pds.simpleapp_javaee.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscadorEventos(@Context HttpServletRequest req,
			@DefaultValue("10") @QueryParam("limite") int limite, // cantidad de resultados a partir del offser indicado
			@DefaultValue("0") @QueryParam("offset") int offset, // posición del primer evento en la lista obtenida
			@DefaultValue("") @QueryParam("titulo") String titulo,
			@QueryParam("deportes") final List<Long> deportes,
			@QueryParam("fechaEvento") Date fechaEvento,
			@DefaultValue("-1") @QueryParam("distancia") Integer distancia) {
		if (estaUsuarioLogeado(req)) {
			List<Evento> eventos = eventoService.buscadorEventos(obtenerUsuarioLogeado(req),limite,offset,titulo,deportes,fechaEvento,distancia);
			return buildResponse(eventos.size());
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

}
