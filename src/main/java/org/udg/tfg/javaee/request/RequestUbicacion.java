package org.udg.tfg.javaee.request;

import javax.validation.constraints.NotNull;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * peticiones recibidas a traves de la API REST sobre las ubicaciones GPS de los
 * dispositivos móviles.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class RequestUbicacion {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para guardar una ubicación GPS de un
	 * dispositivo móvil.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestUbicacionUsuario {

		/**
		 * Indica la latitud de la ubicación GPS.
		 */
		@NotNull
		public double latitud;

		/**
		 * Indica la longitud de la ubicación GPS.
		 */
		@NotNull
		public double longitud;

		/**
		 * Indica la dirección de la ubicación GPS.
		 */
		@NotNull
		public String direccion;

		/**
		 * Indica el municipio de la ubicación GPS.
		 */
		@NotNull
		public String municipio;

	}

}
