package org.udg.tfg.javaee.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.udg.tfg.javaee.model.Evento;
import org.udg.tfg.javaee.model.Usuario;
import org.udg.tfg.javaee.response.ResponseUsuario;
import org.udg.tfg.javaee.service.ParticipanteService;

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

	// tipo = 0 ; Participantes registrados en el evento
	// tipo = 1 ; Participantes en lista de espera del evento
	// tipo = 2 ; Ambos participantes
	@GET
	@Path("{idEvento}/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerParticipantesEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento,@PathParam("tipo") Long tipo) {

		if (estaUsuarioLogeado(req)) {
			List<Usuario> participantes = participanteService.obtenerParticipantesEvento(idEvento,
					obtenerUsuarioLogeado(req),tipo);
			List<ResponseUsuario.ResponseInformacionParticipante> responseParticipantes = new ArrayList<>();
			for (Usuario participante : participantes) {
				responseParticipantes.add(new ResponseUsuario.ResponseInformacionParticipante(participante));
			}
			return buildResponse(responseParticipantes);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}

	}
	


}
