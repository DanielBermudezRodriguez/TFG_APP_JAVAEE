package org.udg.pds.simpleapp_javaee.rest;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.udg.pds.simpleapp_javaee.model.Imagen;
import org.udg.pds.simpleapp_javaee.service.UsuarioService;
import org.udg.pds.simpleapp_javaee.util.Global;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
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

	public static final java.nio.file.Path BASE_DIR = Paths.get(System.getenv(Global.VARIABLE_SISTEMA_IMAGENES));

	@EJB
	UsuarioService usuarioService;

	@POST
	@Path("/usuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response subirImagenUsuario(@Context HttpServletRequest req, MultipartFormDataInput input) {
		if (estaUsuarioLogeado(req)) {
			List<String> imagenSubida = subirImagen(input, obtenerUsuarioLogeado(req));
			Imagen imagen = usuarioService.guardarImagenPerfil(obtenerUsuarioLogeado(req), imagenSubida, BASE_DIR);
			return buildResponse(imagen);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	@GET
	@Path("/usuario/{idUsuario}")
	@Produces({ "image/png", "image/jpg" })
	public Response obtenerImagen(@Context HttpServletRequest req, @PathParam("idUsuario") Long idUsuario) {
		String nombreImagen = usuarioService.obtenerImagen(idUsuario);
		File file = new File(BASE_DIR.toString() + "\\" + nombreImagen);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + nombreImagen);
		return response.build();
	}

	@DELETE
	@Path("/usuario")
	@Produces(MediaType.APPLICATION_JSON)
	public Response eliminarImagenPerfil(@Context HttpServletRequest req) {
		if (estaUsuarioLogeado(req)) {
			Imagen imagen = usuarioService.eliminarImagenPerfil(obtenerUsuarioLogeado(req), BASE_DIR);
			return buildResponse(imagen);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	private List<String> subirImagen(MultipartFormDataInput input, Long idUsuario) {
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		List<String> imagenesSubidas = new ArrayList<>();
		for (InputPart inputPart : inPart) {
			try {
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				String nombreImagen = crearNombreImagen(idUsuario, headers);
				InputStream istream = inputPart.getBody(InputStream.class, null);
				String rutaImagen = System.getenv(Global.VARIABLE_SISTEMA_IMAGENES) + nombreImagen;
				saveFile(istream, rutaImagen);
				imagenesSubidas.add(nombreImagen);
			} catch (Exception e) {
				throw new WebApplicationException("Error al guardar la imagen");
			}
		}

		return imagenesSubidas;
	}

	private String crearNombreImagen(Long idUsuario, MultivaluedMap<String, String> headers) {
		String extension = obtenerExtension(headers);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return "id" + idUsuario + "usuario" + calendar.getTimeInMillis() + "." + extension;
	}

	private void saveFile(InputStream uploadedInputStream, String serverLocation) throws IOException {

		int read;
		byte[] bytes = new byte[1024];

		OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
		while ((read = uploadedInputStream.read(bytes)) != -1) {
			outpuStream.write(bytes, 0, read);
		}
		outpuStream.flush();
		outpuStream.close();
	}

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.MULTIPART_FORM_DATA) public Response upload(@Context
	 * HttpServletRequest req, MultipartFormDataInput input) {
	 * 
	 * Map<String, List<InputPart>> formParts = input.getFormDataMap();
	 * List<InputPart> inPart = formParts.get("file"); List<String> imagenesSubidas
	 * = new ArrayList<>(); for (InputPart inputPart : inPart) {
	 * 
	 * try { MultivaluedMap<String, String> headers = inputPart.getHeaders(); String
	 * fileName = parseFileName(headers); InputStream istream =
	 * inputPart.getBody(InputStream.class, null); fileName =
	 * System.getenv(Global.VARIABLE_SISTEMA_IMAGENES) + fileName; saveFile(istream,
	 * fileName); imagenesSubidas.add(fileName); } catch (Exception e) { throw new
	 * WebApplicationException("Error al guardar la imagen"); } }
	 * 
	 * return buildResponse(imagenesSubidas); }
	 */

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
