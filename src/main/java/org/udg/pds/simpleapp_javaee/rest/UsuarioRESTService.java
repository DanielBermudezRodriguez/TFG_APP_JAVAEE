package org.udg.pds.simpleapp_javaee.rest;

import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.service.UsuarioService;
import org.udg.pds.simpleapp_javaee.util.Global;
import request.RequestUsuario.RequestLoginUsuario;
import request.RequestUsuario.RequestModificarUsuario;
import request.RequestUsuario.RequestRegistroUsuario;
import response.ResponseEvento;
import response.ResponseUsuario;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
@RequestScoped
public class UsuarioRESTService extends GenericRESTService {

	@EJB
	UsuarioService usuarioService;

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response autenticacion(@Context HttpServletRequest req, @Valid RequestLoginUsuario login) {

		if (!estaUsuarioLogeado(req)) {
			Usuario u = usuarioService.verificarPassword(login);
			req.getSession().setAttribute(Global.AUTH_ID, u.getId());
			return buildResponse(new ResponseGenericId(u.getId()));
		} else {
			throw new WebApplicationException("Ya ha iniciado sesión");
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response registro(@Context HttpServletRequest req, @Valid RequestRegistroUsuario registro) {

		if (!estaUsuarioLogeado(req)) {
			Usuario u = usuarioService.registro(registro);
			req.getSession().setAttribute(Global.AUTH_ID, u.getId());
			return buildResponse(new ResponseGenericId(u.getId()));
		}
		throw new WebApplicationException("No se puede registrar un usuario logeado");

	}

	@Path("logout/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest req, @PathParam("id") Long idUsuario) {

		if (estaUsuarioLogeado(req)) {
			Long idSesion = obtenerUsuarioLogeado(req);
			if (idSesion.equals(idUsuario)) {
				HttpSession session = req.getSession();
				session.invalidate();
				return buildResponse(new ResponseGenericId(idUsuario));
			} else {
				throw new WebApplicationException("No se puede cerrar sesión de otros usuarios");
			}
		}
		throw new WebApplicationException("No se puede cerrar sesión si no está autenticado");

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerPerfilUsuario(@Context HttpServletRequest req, @PathParam("id") Long idUsuario) {

		if (estaUsuarioLogeado(req)) {
			Usuario usuario = usuarioService.obtenerPerfilUsuario(idUsuario);
			return buildResponse(new ResponseUsuario.ResponseInformacionUsuario(usuario));

		}
		throw new WebApplicationException("No ha iniciado sesión");
	}

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modificarPerfilUsuario(@Context HttpServletRequest req, @PathParam("id") Long idUsuario,
			@Valid RequestModificarUsuario datosPerfil) {

		if (estaUsuarioLogeado(req)) {
			Long idSesion = obtenerUsuarioLogeado(req);
			if (idSesion.equals(idUsuario)) {
				Usuario u = usuarioService.modificarPerfil(datosPerfil, idUsuario);
				return buildResponse(new ResponseGenericId(u.getId()));
			} else
				throw new WebApplicationException("No se puede modificar datos de otro usuario");

		}
		throw new WebApplicationException("No ha iniciado sesión");

	}

	// tipo = 0 ; Eventos creados y administrados por el usuario.
	// tipo = 1 ; Eventos en los que está apuntado el usuario.
	@GET
	@Path("evento/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerEventosUsuario(@Context HttpServletRequest req, @PathParam("tipo") Long tipo) {

		if (estaUsuarioLogeado(req)) {
			List<ResponseEvento.ResponseEventoInformacion> eventos = usuarioService
					.obtenerEventosUsuario(obtenerUsuarioLogeado(req), tipo);
			return buildResponse(eventos);
		}
		throw new WebApplicationException("No ha iniciado sesión");
	}

}
