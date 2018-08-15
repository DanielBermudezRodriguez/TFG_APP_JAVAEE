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

public class ResponseForo {

	public static class ResponseContenidoForo {

		public Long id;
		public String titulo;
		public Boolean esPublico;
		public List<ResponseMensaje> mensajes = new ArrayList<>();

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

	public static class ResponseMensaje {

		public Long id;
		public String username;
		@JsonSerialize(using = JsonDateSerializer.class)
		@JsonDeserialize(as = JsonDateDeserializer.class)
		public Date fechaMensaje;
		public String mensaje;

		public ResponseMensaje(Mensaje mensaje) {
			this.id = mensaje.getId();
			this.username = mensaje.getUsuario().getUsername();
			this.fechaMensaje = mensaje.getFechaMensaje();
			this.mensaje = mensaje.getMensaje();
		}

	}
}
