package org.udg.tfg.javaee.response;

import org.udg.tfg.javaee.model.Municipio;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * respuestas a enviar a través de la API REST a peticiones sobre los municipios
 * disponibles en la aplicación.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class ResponseMunicipio {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la información de un
	 * municipio.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseInformacionMunicipio {

		/**
		 * identificador del municipio.
		 */
		public Long id;

		/**
		 * Nombre del municipio.
		 */
		public String municipio;

		/**
		 * Constructor de la respuesta a enviar a una petición de la información de un
		 * municipio.
		 * 
		 * @param m
		 *            Contiene la información del municipio a procesar.
		 * @return Devuelve la información del municipio.
		 */
		public ResponseInformacionMunicipio(Municipio m) {
			this.id = m.getId();
			this.municipio = m.getMunicipio();
		}

	}

}
