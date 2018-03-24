package org.udg.pds.simpleapp_javaee.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.service.ParticipanteService;

@Path("/participante")
@RequestScoped
public class ParticipanteRESTService extends GenericRESTService {

	@EJB
	ParticipanteService participanteService;

	@POST
	@Path("{idEvento}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addParticipanteEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento) {

		if (estaUsuarioLogeado(req)) {
			Evento evento = participanteService.addParticipanteEvento(obtenerUsuarioLogeado(req), idEvento);
			return buildResponse(new ResponseGenericId(evento.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

	@DELETE
	@Path("{idEvento}/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarParticipanteEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento,
			@PathParam("idUsuario") Long idUsuario) {

		if (estaUsuarioLogeado(req)) {
			Evento evento = participanteService.eliminarParticipanteEvento(idUsuario, idEvento,
					obtenerUsuarioLogeado(req));
			return buildResponse(new ResponseGenericId(evento.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}

}
