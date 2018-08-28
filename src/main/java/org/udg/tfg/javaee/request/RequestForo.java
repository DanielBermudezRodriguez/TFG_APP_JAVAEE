package org.udg.tfg.javaee.request;

import javax.validation.constraints.NotNull;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * peticiones recibidas a traves de la API REST sobre los mensajes del foro de
 * un evento deportivo.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class RequestForo {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para guardar un mensaje del foro en un
	 * evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestMensajeForo {

		/**
		 * Identificador del foro.
		 */
		@NotNull
		public Long idForo;

		/**
		 * Contenido del mensaje a guardar.
		 */
		@NotNull
		public String mensaje;
	}

}
