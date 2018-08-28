package org.udg.tfg.javaee.request;

import javax.validation.constraints.NotNull;
import org.udg.tfg.javaee.request.RequestUbicacion.RequestUbicacionUsuario;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * peticiones recibidas a traves de la API REST sobre la creación de eventos
 * deportivos.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class RequestEvento {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para crear un evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestCrearEvento {
		/**
		 * Título del evento deportivo.
		 */
		@NotNull
		public String titulo;
		/**
		 * descripción del evento deportivo.
		 */
		@NotNull
		public String descripcion;
		/**
		 * Duración estimada del evento deportivo.
		 */
		@NotNull
		public int duracion;
		/**
		 * Número de participantes del evento deportivo.
		 */
		@NotNull
		public int numeroParticipantes;
		/**
		 * Fecha en la que se celebrará el evento deportivo.
		 */
		@NotNull
		public String fechaEvento;
		/**
		 * Define si el foro del evento deportivo es público o privado.
		 */
		@NotNull
		public Boolean esPublico;
		/**
		 * Identificador del municipio en que se celebra el evento deportivo.
		 */
		public Long municipio;
		/**
		 * Identificador de la categoría deportivo del evento deportivo.
		 */
		@NotNull
		public Long deporte;
		/**
		 * Información de la ubicación GPS en la que se celebrará el evento deportivo.
		 */
		public RequestUbicacionUsuario ubicacionGPS;
		/**
		 * Título del foro del evento deportivo.
		 */
		@NotNull
		public String tituloForo;
	}

}
