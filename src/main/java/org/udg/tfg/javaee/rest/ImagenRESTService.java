package org.udg.tfg.javaee.rest;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.udg.tfg.javaee.model.Imagen;
import org.udg.tfg.javaee.service.EventoService;
import org.udg.tfg.javaee.service.UsuarioService;
import org.udg.tfg.javaee.util.Global;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/imagen")
@RequestScoped
public class ImagenRESTService extends GenericRESTService {

	public static final java.nio.file.Path BASE_DIR_IMAGES_USERS = Paths
			.get(System.getenv(Global.VARIABLE_ENTORNO_IMAGENES_USUARIOS));
	public static final java.nio.file.Path BASE_DIR_IMAGES_EVENTS = Paths
			.get(System.getenv(Global.VARIABLE_ENTORNO_IMAGENES_EVENTOS));

	@EJB
	UsuarioService usuarioService;

	@EJB
	EventoService eventoService;

	@POST
	@Path("/evento/{idEvento}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response subirImagenEvento(@Context HttpServletRequest req, MultipartFormDataInput input,
			@PathParam("idEvento") Long idEvento) {
		if (estaUsuarioLogeado(req)) {
			List<String> imagenSubida = subirImagenEvento(input, idEvento);
			Imagen imagen = eventoService.guardarImagenEvento(idEvento, imagenSubida, BASE_DIR_IMAGES_EVENTS);
			return buildResponse(imagen);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	@POST
	@Path("/usuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response subirImagenUsuario(@Context HttpServletRequest req, MultipartFormDataInput input) {
		if (estaUsuarioLogeado(req)) {
			List<String> imagenSubida = subirImagenUsuario(input, obtenerUsuarioLogeado(req));
			Imagen imagen = usuarioService.guardarImagenPerfil(obtenerUsuarioLogeado(req), imagenSubida,
					BASE_DIR_IMAGES_USERS);
			return buildResponse(imagen);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	@GET
	@Path("/usuario/nombre/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerNombreImagenUsuario(@Context HttpServletRequest req,
			@PathParam("idUsuario") Long idUsuario) {
		String nombreImagen = usuarioService.obtenerImagen(idUsuario);
		return buildResponse(nombreImagen);
	}

	@GET
	@Path("/evento/nombre/{idEvento}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obtenerNombreImagenEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento) {
		String nombreImagen = eventoService.obtenerImagen(idEvento);
		return buildResponse(nombreImagen);
	}

	@GET
	@Path("/usuario/{idUsuario}/{nombreImagen}")
	@Produces({ "image/png", "image/jpg" })
	public Response obtenerImagenUsuario(@Context HttpServletRequest req, @PathParam("idUsuario") Long idUsuario,
			@PathParam("nombreImagen") String nombreImg) {
		String nombreImagen = usuarioService.obtenerImagen(idUsuario);
		File file = new File(BASE_DIR_IMAGES_USERS.toString() + "\\" + nombreImagen);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + nombreImagen);
		return response.build();
	}

	@GET
	@Path("/evento/{idEvento}/{nombreImagen}")
	@Produces({ "image/png", "image/jpg" })
	public Response obtenerImagenEvento(@Context HttpServletRequest req, @PathParam("idEvento") Long idEvento,
			@PathParam("nombreImagen") String nombreImg) {
		// String nombreImagen = usuarioService.obtenerImagen(idUsuario);
		String nombreImagen = eventoService.obtenerImagen(idEvento);
		File file = new File(BASE_DIR_IMAGES_EVENTS.toString() + "\\" + nombreImagen);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + nombreImagen);
		return response.build();
	}

	@DELETE
	@Path("/usuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarImagenPerfil(@Context HttpServletRequest req) {
		if (estaUsuarioLogeado(req)) {
			Imagen imagen = usuarioService.eliminarImagenPerfil(obtenerUsuarioLogeado(req), BASE_DIR_IMAGES_USERS);
			return buildResponse(imagen);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	private List<String> subirImagenUsuario(MultipartFormDataInput input, Long idUsuario) {
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		List<String> imagenesSubidas = new ArrayList<>();
		for (InputPart inputPart : inPart) {
			try {
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				String nombreImagen = crearNombreImagen(idUsuario, headers);
				InputStream istream = inputPart.getBody(InputStream.class, null);
				String rutaImagen = System.getenv(Global.VARIABLE_ENTORNO_IMAGENES_USUARIOS) + "\\" + nombreImagen;
				saveFile(istream, rutaImagen);
				imagenesSubidas.add(nombreImagen);
			} catch (Exception e) {
				throw new WebApplicationException("Error al guardar la imagen");
			}
		}

		return imagenesSubidas;
	}

	private List<String> subirImagenEvento(MultipartFormDataInput input, Long idEvento) {
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		List<String> imagenesSubidas = new ArrayList<>();
		for (InputPart inputPart : inPart) {
			try {
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				String nombreImagen = crearNombreImagen(idEvento, headers);
				InputStream istream = inputPart.getBody(InputStream.class, null);
				String rutaImagen = System.getenv(Global.VARIABLE_ENTORNO_IMAGENES_EVENTOS) + "\\" + nombreImagen;
				saveFile(istream, rutaImagen);
				imagenesSubidas.add(nombreImagen);
			} catch (Exception e) {
				throw new WebApplicationException("Error al guardar la imagen");
			}
		}

		return imagenesSubidas;
	}

	private String crearNombreImagen(Long id, MultivaluedMap<String, String> headers) {
		String extension = obtenerExtension(headers);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return "id" + id + "yourSport" + calendar.getTimeInMillis() + "." + extension;
	}

	private void saveFile(InputStream uploadedInputStream, String serverLocation) throws IOException {

		BufferedImage original = ImageIO.read(uploadedInputStream);
		File output = new File(serverLocation);
		ImageIO.write(original, "jpg", output);

	}

	private String obtenerExtension(MultivaluedMap<String, String> headers) {

		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		String extension = null;
		for (String name : contentDispositionHeader) {

			if ((name.trim().startsWith("filename"))) {

				String[] tmp = name.split("=");

				String fileName = tmp[1].trim().replaceAll("\"", "");

				int i = fileName.lastIndexOf('.');
				if (i >= 0) {
					extension = fileName.substring(i + 1);
				}

				return extension;
			}
		}
		return "randomName";
	}

}
