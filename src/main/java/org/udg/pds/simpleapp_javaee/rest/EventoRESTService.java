package org.udg.pds.simpleapp_javaee.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.service.EventoService;
import request.RequestEvento.RequestCrearEvento;
import response.ResponseEvento;

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

	@PUT
	@Path("{idUsuario}/{idEvento}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarEvento(@Context HttpServletRequest req, @PathParam("idUsuario") Long idUsuario, @PathParam("idEvento") Long idEvento,
			@Valid RequestCrearEvento datosEvento) {

		if (estaUsuarioLogeado(req)) {
			Long idSesion = obtenerUsuarioLogeado(req);
			if (idSesion.equals(idUsuario)) {
				Evento e = eventoService.modificarEventoDeportivo(datosEvento, idUsuario, idEvento);
				return buildResponse(new ResponseGenericId(e.getId()));
			} else
				throw new WebApplicationException("No puede modificar eventos de otros usuarios");
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

	@Path("{idEvento}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento) {
		if (estaUsuarioLogeado(req)) {
			Evento e = eventoService.cancelarEvento(obtenerUsuarioLogeado(req), idEvento);
			return buildResponse(new ResponseGenericId(e.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscadorEventos(@Context HttpServletRequest req,
			@DefaultValue("10") @QueryParam("limite") int limite, // cantidad de resultados a partir del offset indicado
			@DefaultValue("0") @QueryParam("offset") int offset, // posición del primer evento en la lista obtenida
			@DefaultValue("") @QueryParam("titulo") String titulo, @QueryParam("deportes") final List<Long> deportes,
			@QueryParam("fechaEvento") String fechaEvento,
			@DefaultValue("-1") @QueryParam("distancia") Integer distancia,
			@DefaultValue("-1") @QueryParam("municipio") Long municipio) {
		if (estaUsuarioLogeado(req)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date fecha = null;
			try {
				if (fechaEvento != null && !fechaEvento.isEmpty())
					fecha = sdf.parse(fechaEvento);
				List<ResponseEvento.ResponseEventoInformacion> eventos = eventoService.buscadorEventos(
						obtenerUsuarioLogeado(req), limite, offset, titulo, deportes, fecha, distancia, municipio);
				return buildResponse(eventos);
			} catch (ParseException e) {
				throw new WebApplicationException("Error en la búsqueda de eventos");
			}
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	@GET
	@Path("{idEvento}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento) {

		if (estaUsuarioLogeado(req)) {
			ResponseEvento.ResponseEventoInformacion evento = eventoService.obtenerEvento(idEvento);
			return buildResponse(evento);
		}
		throw new WebApplicationException("No ha iniciado sesión");

	}

}
