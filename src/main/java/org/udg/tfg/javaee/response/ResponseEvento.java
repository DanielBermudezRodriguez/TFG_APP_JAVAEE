package org.udg.tfg.javaee.response;

import java.util.Date;
import org.udg.tfg.javaee.model.Deporte;
import org.udg.tfg.javaee.model.Estado;
import org.udg.tfg.javaee.model.Evento;
import org.udg.tfg.javaee.model.Foro;
import org.udg.tfg.javaee.model.Municipio;
import org.udg.tfg.javaee.model.Provincia;
import org.udg.tfg.javaee.model.Ubicacion;
import org.udg.tfg.javaee.model.Usuario;
import org.udg.tfg.javaee.rest.serializer.JsonDateDeserializer;
import org.udg.tfg.javaee.rest.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * respuestas a enviar a través de la API REST a peticiones recibidas sobre los
 * eventos deportivos
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class ResponseEvento {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la información de un
	 * evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseEventoInformacion {

		/**
		 * Indica el identificador del evento deportivo.
		 */
		public Long id;

		/**
		 * Indica el título del evento deportivo.
		 */
		public String titulo;

		/**
		 * Indica la descripción del evento deportivo.
		 */
		public String descripcion;

		/**
		 * Indica la duración del evento deportivo.
		 */
		public int duracion;

		/**
		 * Indica el número de participantes del evento deportivo.
		 */
		public int numeroParticipantes;

		/**
		 * Indica la fecha en que se celebrará el evento deportivo.
		 */
		@JsonSerialize(using = JsonDateSerializer.class)
		@JsonDeserialize(as = JsonDateDeserializer.class)
		public Date fechaEvento;

		/**
		 * Indica la categoría deportiva del evento deportivo.
		 */
		public Deporte deporte;

		/**
		 * Indica el estado del evento deportivo.
		 */
		public Estado estado;

		/**
		 * Indica el número de participantes registrados en el evento deportivo.
		 */
		public int participantesRegistrados;

		/**
		 * Indica el administrador del evento deportivo.
		 */
		public ResponseAdministradorEvento administrador;

		/**
		 * Indica el municipio del evento deportivo.
		 */
		public Municipio municipio;

		/**
		 * Indica la provincia del evento deportivo.
		 */
		public Provincia provincia;

		/**
		 * Indica el foro del evento deportivo.
		 */
		public Foro foro;

		/**
		 * Indica la ubicación GPS del evento deportivo.
		 */
		public Ubicacion ubicacion;

		/**
		 * Constructor de la respuesta a enviar a una petición de la información de un
		 * evento deportivo.
		 * 
		 * @param e
		 *            Contiene la información del evento deportivo a procesar.
		 * @param participantesRegistrados
		 *            Número de participantes registrados en el evento deportivo.
		 * @return Devuelve la información del evento deportivo.
		 */
		public ResponseEventoInformacion(Evento e, Integer participantesRegistrados) {
			this.id = e.getId();
			this.titulo = e.getTitulo();
			this.descripcion = e.getDescripcion();
			this.duracion = e.getDuracion();
			this.numeroParticipantes = e.getNumeroParticipantes();
			this.fechaEvento = e.getFechaEvento();
			this.deporte = e.getDeporte();
			this.estado = e.getEstado();
			this.participantesRegistrados = participantesRegistrados;
			this.administrador = new ResponseAdministradorEvento(e.getAdministrador());
			if (e.getUbicacionGPS() != null) {
				this.municipio = e.getUbicacionGPS().getMunicipio();
				this.provincia = e.getUbicacionGPS().getMunicipio().getProvincia();
				this.ubicacion = e.getUbicacionGPS();
			} else if (e.getMunicipio() != null) {
				this.municipio = e.getMunicipio();
				this.provincia = e.getMunicipio().getProvincia();
				this.ubicacion = null;
			}
			this.foro = e.getForo();

		}

	}

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la información del
	 * administrador de un evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseAdministradorEvento {

		/**
		 * Identificador del administrador del evento deportivo.
		 */
		public Long id;

		/**
		 * Nick del administrador del evento deportivo.
		 */
		public String username;

		/**
		 * Correo electrónico del administrador del evento deportivo.
		 */
		public String email;

		/**
		 * Constructor de la respuesta a enviar a una petición de la información del
		 * administrador de un evento deportivo.
		 * 
		 * @param u
		 *            Contiene la información del administrador del evento deportivo.
		 * @return Devuelve la información del administrador del evento deportivo.
		 */
		public ResponseAdministradorEvento(Usuario u) {
			this.id = u.getId();
			this.username = u.getUsername();
			this.email = u.getEmail();
		}

	}

}
