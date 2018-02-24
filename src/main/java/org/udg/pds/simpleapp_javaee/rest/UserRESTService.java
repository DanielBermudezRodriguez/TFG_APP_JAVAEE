package org.udg.pds.simpleapp_javaee.rest;

import org.udg.pds.simpleapp_javaee.model.User;
import org.udg.pds.simpleapp_javaee.model.Views;
import org.udg.pds.simpleapp_javaee.service.UserService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// Clase utilizada para recibir todas las peticiones REST relacionadas con los usuarios
@Path("/users")
@RequestScoped
public class UserRESTService extends RESTService {

  // EJB utilizada para la lógica de negocio de las operaciones con usuarios
  @EJB
  UserService userService;

  @Inject
  ToJSON toJSON;

  @POST
  @Path("auth")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response auth(@Context HttpServletRequest req,@CookieParam("JSESSIONID") Cookie cookie, @Valid LoginUser user) {
	  
	// Comprovar que el usuario no está logeado
    checkNotLoggedIn(req);
    // Devuelve el usuario si existe un usuario con el mail y contraseña pasados por parámetro
    User u = userService.matchPassword(user.email, user.password);
    // Asignamos al identificador de sesión el identificador del usuario
    req.getSession().setAttribute("simpleapp_auth_id", u.getId());
    return buildResponseWithView(Views.Private.class, u);
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteUser(@Context HttpServletRequest req, @PathParam("id") Long userId) {
	  
	// Comprueva si el usuario está logeado, si lo está devuelve el identificador de sesión  
    Long loggedUserId = getLoggedUser(req);

    if (!loggedUserId.equals(userId))
      throw new WebApplicationException("No se pueden borrar otros usuarios!");

    return buildResponse(userService.remove(userId));
    
  }


  @Path("register")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(RegisterUser ru, @Context HttpServletRequest req) {

    try {
      // Comprueva si el usuario está logeado, si lo está devuelve el identificador de sesión  
      Long userId = getLoggedUser(req);
    } catch (WebApplicationException ex) {
      // Usuario no está logeado puede registrarse
      return buildResponse(userService.register(ru.username, ru.email, ru.password));
    }

    throw new WebApplicationException("No se puede registrar un usuario logeado.");
  }

  // Clase estática que contiene los parámetros del login de un usuario
  static class LoginUser {
    @NotNull public String email;
    @NotNull public String password;
  }

  // Clase estática con atributos necesarios para el registro de un nuevo usuario
  static class RegisterUser {
    @NotNull public String username;
    @NotNull public String email;
    @NotNull public String password;
  }

  // Clase estática con variable identificador
  static class ID {
    public Long id;

    public ID(Long id) {
      this.id = id;
    }
  }

}
