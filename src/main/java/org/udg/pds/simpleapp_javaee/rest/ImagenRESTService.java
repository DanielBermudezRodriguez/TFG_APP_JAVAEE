package org.udg.pds.simpleapp_javaee.rest;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.udg.pds.simpleapp_javaee.util.Global;
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
import java.util.List;
import java.util.Map;

@Path("/imagen")
@RequestScoped
public class ImagenRESTService extends GenericRESTService {

	public static final java.nio.file.Path BASE_DIR = Paths.get(System.getenv(Global.VARIABLE_SISTEMA_IMAGENES));

	@POST
	@Path("/usuario")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response subirImagenUsuario(@Context HttpServletRequest req, MultipartFormDataInput input) {
		if (estaUsuarioLogeado(req)) {
			List<String> imagenSubida = subirImagen(input);
			return buildResponse(imagenSubida);
		} else {
			throw new WebApplicationException("No ha iniciado sesión");
		}
	}

	private List<String> subirImagen(MultipartFormDataInput input) {
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		List<String> imagenesSubidas = new ArrayList<>();
		for (InputPart inputPart : inPart) {

			try {
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				String fileName = parseFileName(headers);
				InputStream istream = inputPart.getBody(InputStream.class, null);
				fileName = System.getenv(Global.VARIABLE_SISTEMA_IMAGENES) + fileName;
				saveFile(istream, fileName);
				imagenesSubidas.add(fileName);
			} catch (Exception e) {
				throw new WebApplicationException("Error al guardar la imagen");
			}
		}

		return imagenesSubidas;
	}

	@GET
	@Path("{nombreFichero}")
	@Produces({ "image/png", "image/jpg" })
	public Response obtenerImagen(@PathParam("nombreFichero") String nombreFichero) {

		File file = new File(BASE_DIR.toString() + "\\" + nombreFichero);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + nombreFichero);
		return response.build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(@Context HttpServletRequest req, MultipartFormDataInput input) {

		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		List<String> imagenesSubidas = new ArrayList<>();
		for (InputPart inputPart : inPart) {

			try {
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				String fileName = parseFileName(headers);
				InputStream istream = inputPart.getBody(InputStream.class, null);
				fileName = System.getenv(Global.VARIABLE_SISTEMA_IMAGENES) + fileName;
				saveFile(istream, fileName);
				imagenesSubidas.add(fileName);
			} catch (Exception e) {
				throw new WebApplicationException("Error al guardar la imagen");
			}
		}

		return buildResponse(imagenesSubidas);
	}

	private String parseFileName(MultivaluedMap<String, String> headers) {

		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");

		for (String name : contentDispositionHeader) {

			if ((name.trim().startsWith("filename"))) {

				String[] tmp = name.split("=");

				String fileName = tmp[1].trim().replaceAll("\"", "");

				return fileName;
			}
		}
		return "randomName";
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
}
