package org.udg.pds.simpleapp_javaee.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.service.TaskService;
import org.udg.pds.simpleapp_javaee.util.ToJSON;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

@Path("/tasks")
@RequestScoped
public class TaskRESTService extends RESTService {

  @EJB
  TaskService taskService;

  @Inject
  ToJSON toJSON;

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTask(@Context HttpServletRequest req,@PathParam("id") Long id) {
	// Obtenemos el identificador de sesión del usuario  
    Long userId = getLoggedUser(req);
    return buildResponse(taskService.getTask(userId, id));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response listAllTasks(@Context HttpServletRequest req) {
	// Obtenemos el identificador de sesión del usuario  
	Long userId = getLoggedUser(req);
    return buildResponse(taskService.getTasks(userId));
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addTask(R_Task task, @Context HttpServletRequest req) throws ParseException {
	// Obtenemos el identificador de sesión del usuario  
    Long userId = getLoggedUser(req);

    if (task.text == null) {
      throw new WebApplicationException("No se ha indicado el texto de la tarea.");
    }
    if (task.dateCreated == null) {
      throw new WebApplicationException("No se ha indicado la fecha de creación de la tarea.");
    }
    if (task.dateLimit == null) {
      throw new WebApplicationException("No se ha indicado la fecha límite de la tarea.");
    }

    return buildResponse(taskService.addTask(task.text, userId, task.dateCreated, task.dateLimit));
  }

  @DELETE
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteTask(@Context HttpServletRequest req,
                             @PathParam("id") Long taskId) {
    getLoggedUser(req);

    return buildResponse(taskService.remove(taskId));
  }

  @POST
  @Path("{id}/tags")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addTags(Collection<Long> tags, @Context HttpServletRequest req,
                        @PathParam("id") Long taskId) {

    Long userId = getLoggedUser(req);

    taskService.addTagsToTask(userId, taskId, tags);
    return Response.ok().build();
  }

  @GET
  @Path("{id}/tags")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getTaskTags(@Context HttpServletRequest req,@PathParam("id") Long taskId) {

    Long userId = getLoggedUser(req);

    return buildResponse(taskService.getTaskTags(userId, taskId));
  }

  // Clase estática con los datos necesarios para crear una tarea.
  static class R_Task {

    public String text;

    @JsonDeserialize(using=JsonDateDeserializer.class)
    public Date dateCreated;

    @JsonDeserialize(using=JsonDateDeserializer.class)
    public Date dateLimit;
  }

}