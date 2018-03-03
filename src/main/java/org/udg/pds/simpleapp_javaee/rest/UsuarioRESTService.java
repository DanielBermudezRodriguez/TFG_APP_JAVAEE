package org.udg.pds.simpleapp_javaee.rest;

import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.model.Views;
import org.udg.pds.simpleapp_javaee.service.UsuarioService;
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
@Path("/usuarios")
@RequestScoped
public class UsuarioRESTService extends RESTService {

  // EJB utilizada para la lógica de negocio de las operaciones con usuarios
  @EJB
  UsuarioService usuarioService;

  @Inject
  ToJSON toJSON;

  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response autenticacion(@Context HttpServletRequest req, @Valid LoginUsuario usuario) {
	  
	// Comprovar que el usuario no está logeado
    checkNotLoggedIn(req);
    // Devuelve el usuario si existe un usuario con el mail y contraseña pasados por parámetro
    Usuario u = usuarioService.verificarPassword(usuario.email, usuario.password);
    // Asignamos al identificador de sesión el identificador del usuario
    req.getSession().setAttribute("simpleapp_auth_id", u.getId());
    return buildResponseWithView(Views.Private.class, u);
  }
  
  @Path("registro")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response registro(RegistroUsuario ru, @Context HttpServletRequest req) {

    try {
      // Comprueva si el usuario está logeado, si lo está devuelve el identificador de sesión  
      Long userId = getLoggedUser(req);
    } catch (WebApplicationException ex) {
      // Usuario no está logeado puede registrarse
      return buildResponseWithView(Views.Private.class, usuarioService.registro(ru.username, ru.email, ru.password, ru.nombre, ru.apellidos, ru.telefono));
    }

    throw new WebApplicationException("No se puede registrar un usuario logeado.");
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteUser(@Context HttpServletRequest req, @PathParam("id") Long userId) {
	  
	// Comprueva si el usuario está logeado, si lo está devuelve el identificador de sesión  
    Long loggedUserId = getLoggedUser(req);

    if (!loggedUserId.equals(userId))
      throw new WebApplicationException("No se pueden borrar otros usuarios!");

    return buildResponse(usuarioService.remove(userId));
    
  }

  // Clase estática que contiene los parámetros del login de un usuario
  static class LoginUsuario {
    @NotNull public String email;
    @NotNull public String password;
  }

  // Clase estática con atributos necesarios para el registro de un nuevo usuario
  static class RegistroUsuario {
    @NotNull public String username;
    @NotNull public String email;
    @NotNull public String password;
    @NotNull public String nombre;
    @NotNull public String apellidos;
    @NotNull public String telefono;
  }

  // Clase estática con variable identificador
  static class ID {
    public Long id;

    public ID(Long id) {
      this.id = id;
    }
  }

}
