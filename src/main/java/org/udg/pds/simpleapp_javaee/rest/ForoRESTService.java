package org.udg.pds.simpleapp_javaee.rest;

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

import org.udg.pds.simpleapp_javaee.model.Foro;
import org.udg.pds.simpleapp_javaee.model.Mensaje;
import org.udg.pds.simpleapp_javaee.service.ForoService;

import request.RequestForo.RequestMensajeForo;
import response.ResponseForo;

@Path("/foro")
@RequestScoped
public class ForoRESTService extends GenericRESTService {

	@EJB
	ForoService foroService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response enviarMensajeForo(@Context HttpServletRequest req, @Valid RequestMensajeForo mensajeForo) {
		if (estaUsuarioLogeado(req)) {
			Long idUsuario = obtenerUsuarioLogeado(req);
			Mensaje m = foroService.enviarMensajeForo(mensajeForo, idUsuario);
			return buildResponse(new ResponseGenericId(m.getId()));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	@GET
	@Path("{idEvento}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerForo(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento) {
		if (estaUsuarioLogeado(req)) {
			Long idUsuario = obtenerUsuarioLogeado(req);
			Foro f = foroService.obtenerForo(idEvento, idUsuario);
			return buildResponse(new ResponseForo.ResponseContenidoForo(f));
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

}
