package org.udg.tfg.javaee.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.udg.tfg.javaee.model.Foro;
import org.udg.tfg.javaee.model.Mensaje;
import org.udg.tfg.javaee.rest.serializer.JsonDateDeserializer;
import org.udg.tfg.javaee.rest.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * respuestas a enviar a través de la API REST a peticiones recibidas sobre el
 * foro de un evento deportivo.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class ResponseForo {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener los mensajes del foro de
	 * un evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseContenidoForo {

		/**
		 * Identificador del foro.
		 */
		public Long id;

		/**
		 * Título del foro.
		 */
		public String titulo;

		/**
		 * Indica si es un foro público privado.
		 */
		public Boolean esPublico;

		/**
		 * Listado de los mensajes enviados en el foro.
		 */
		public List<ResponseMensaje> mensajes = new ArrayList<>();

		/**
		 * Constructor de la respuesta a enviar a una petición de la información del
		 * foro de un evento deportivo.
		 * 
		 * @param foro
		 *            Contiene la información del foro a enviar.
		 * @return Devuelve la información del foro.
		 */
		public ResponseContenidoForo(Foro foro) {
			this.id = foro.getId();
			this.titulo = foro.getTitulo();
			this.esPublico = foro.getEsPublico();
			List<Mensaje> mensajes = foro.getMensajes();
			for (Mensaje mensajeForo : mensajes) {
				ResponseMensaje mensaje = new ResponseMensaje(mensajeForo);
				this.mensajes.add(mensaje);
			}

		}

	}

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener los mensajes del foro de
	 * un evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseMensaje {

		/**
		 * Identificador del mensaje.
		 */
		public Long id;

		/**
		 * Nick del usuario que ha enviado el mensaje.
		 */
		public String username;

		/**
		 * Fecha en la que se ha enviado el mensaje.
		 */
		@JsonSerialize(using = JsonDateSerializer.class)
		@JsonDeserialize(as = JsonDateDeserializer.class)
		public Date fechaMensaje;

		/**
		 * Contenido del mensaje.
		 */
		public String mensaje;

		/**
		 * Constructor de la respuesta a enviar a una petición de los mensaje de un
		 * foro.
		 * 
		 * @param mensaje
		 *            Contenido del mensaje.
		 * @return Devuelve la información del mensaje.
		 */
		public ResponseMensaje(Mensaje mensaje) {
			this.id = mensaje.getId();
			this.username = mensaje.getUsuario().getUsername();
			this.fechaMensaje = mensaje.getFechaMensaje();
			this.mensaje = mensaje.getMensaje();
		}

	}
}
