package org.udg.tfg.javaee.response;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * respuestas a enviar a través de la API REST a peticiones sobre la ubicación
 * GPS de un evento deportivo.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class ResponseUbicacion {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la ubicación GPS de un
	 * evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseUbicacionEvento {

		/**
		 * Indica la latitud de la ubicación GPS.
		 */
		public Double latitud;

		/**
		 * Indica la longitud de la ubicación GPS.
		 */
		public Double longitud;

		/**
		 * Indica la dirección de la ubicación GPS.
		 */
		public String direccion;

		/**
		 * Indica el municipio de la ubicación GPS.
		 */
		public String municipio;

		/**
		 * Constructor de la respuesta a enviar a una petición de ubicación GPS de un
		 * evento deportivo.
		 * 
		 * @param latitud
		 *            Contiene la latitud del evento deportivo.
		 * @param longitud
		 *            Contiene la longitud del evento deportivo.
		 * @param direccion
		 *            Contiene la direccion del evento deportivo.
		 * @param municipio
		 *            Contiene el municipio del evento deportivo.
		 * @return Devuelve la información de la ubicación GPS de un evento deportivo.
		 */
		public ResponseUbicacionEvento(Double latitud, Double longitud, String direccion, String municipio) {
			this.latitud = latitud;
			this.longitud = longitud;
			this.direccion = direccion;
			this.municipio = municipio;
		}

	}

}
