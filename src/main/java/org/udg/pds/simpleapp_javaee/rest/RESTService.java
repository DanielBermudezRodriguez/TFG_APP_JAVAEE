package org.udg.pds.simpleapp_javaee.rest;

import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.ToJSON;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;


public class RESTService {

  @Inject
  protected ToJSON toJSON;

  protected Long getLoggedUser(@Context HttpServletRequest req) {
	// Accesso a la sesión http
    HttpSession session = req.getSession();

    if (session == null) {
    	throw new WebApplicationException("No hay sesiones disponibles!");
    }

    Long userId = (Long)session.getAttribute("simpleapp_auth_id");

    if (userId == null)
      throw new WebApplicationException("El usuario no está autenticado!");

    return userId;
  }

  protected void checkNotLoggedIn(@Context HttpServletRequest req) {
    // Accesso a la sesión http
    HttpSession session = req.getSession();

    if (session == null) {
      throw new WebApplicationException("No hay sesiones disponibles!");
    }

    Long userId = (Long)session.getAttribute("simpleapp_auth_id");

    if (userId != null)
      throw new WebApplicationException("El usuario ya está autenticado!");
  }

  // Respuesta json con el objeto pasadopor parámetro
  protected Response buildResponse(Object o) {
    return Response.ok(o).build();
  }

  // Devuelve un JSON con vista personalizada del objeto
  protected Response buildResponseWithView(Class<?> view, Usuario u) {
    try {
      return Response.ok(toJSON.Object(view, u)).build();
    } catch (IOException e) {
      throw new WebApplicationException("Error al serializar la respuesta con vista");
    }
  }

  // Clase estática con variable identificador
  public static class ID {
    public Long id;

    public ID(Long id) { this.id = id; }
  }
}
