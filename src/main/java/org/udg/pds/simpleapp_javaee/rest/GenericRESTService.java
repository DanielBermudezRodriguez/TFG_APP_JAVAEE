package org.udg.pds.simpleapp_javaee.rest;

import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.Global;
import org.udg.pds.simpleapp_javaee.util.ToJSON;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class GenericRESTService {

	@Inject
	protected ToJSON toJSON;

	protected Long obtenerUsuarioLogeado(@Context HttpServletRequest req) {

		HttpSession session = req.getSession();

		if (session == null) {
			throw new WebApplicationException("Error al obtener la sesión");
		}

		return (Long) session.getAttribute(Global.AUTH_ID);

	}

	protected Boolean estaUsuarioLogeado(@Context HttpServletRequest req) {

		HttpSession session = req.getSession();

		if (session == null) {
			throw new WebApplicationException("Error al obtener la sesión");
		}

		return ((Long) session.getAttribute(Global.AUTH_ID)) != null;

	}

	protected Response buildResponse(Object o) {
		try {
			return Response.ok(toJSON.Object(o)).build();
		} catch (IOException e) {
			throw new WebApplicationException("Error al serializar la respuesta");
		}
	}

	protected Response buildResponseWithView(Class<?> view, Usuario u) {
		try {
			return Response.ok(toJSON.Object(view, u)).build();
		} catch (IOException e) {
			throw new WebApplicationException("Error al serializar la respuesta con vista");
		}
	}

	public static class ResponseGenericId {
		public Long id;

		public ResponseGenericId(Long id) {
			this.id = id;
		}
	}

}
