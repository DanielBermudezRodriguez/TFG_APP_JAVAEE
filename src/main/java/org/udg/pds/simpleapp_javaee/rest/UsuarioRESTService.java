package org.udg.pds.simpleapp_javaee.rest;

import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.service.UsuarioService;
import org.udg.pds.simpleapp_javaee.util.Global;
import request.RequestUsuario.RequestLoginUsuario;
import request.RequestUsuario.RequestRegistroUsuario;
import response.ResponseUsuario;
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
			return buildResponse(new ResponseUsuario.ResponseLoginUsuario(u));
		} else {
			throw new WebApplicationException("Ya ha iniciado sesión");
		}

	}

	@Path("registro")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response registro(@Context HttpServletRequest req, @Valid RequestRegistroUsuario registro) {

		if (!estaUsuarioLogeado(req)) {
			Usuario u = usuarioService.registro(registro);
			req.getSession().setAttribute(Global.AUTH_ID, u.getId());
			return buildResponse(new ResponseUsuario.ResponseRegistroUsuario(u));
		}
		throw new WebApplicationException("No se puede registrar un usuario logeado.");

	}

	@Path("logout/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest req, @PathParam("id") Long idUsuario) {

		if (!estaUsuarioLogeado(req)) {
			Long idSesion = obtenerUsuarioLogeado(req);
			if (idSesion.equals(idUsuario)) {
				HttpSession session = req.getSession();
				session.invalidate();
				return buildResponse(idUsuario);
			} else {
				throw new WebApplicationException("No se puede cerrar sesión de otros usuarios.");
			}
		}
		throw new WebApplicationException("No se puede cerrar sesión sino está autenticado.");

	}

}
