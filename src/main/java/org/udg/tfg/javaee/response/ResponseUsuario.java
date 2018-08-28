package org.udg.tfg.javaee.response;

import java.util.Date;
import java.util.List;
import org.udg.tfg.javaee.model.Deporte;
import org.udg.tfg.javaee.model.Municipio;
import org.udg.tfg.javaee.model.Pais;
import org.udg.tfg.javaee.model.Provincia;
import org.udg.tfg.javaee.model.Usuario;
import org.udg.tfg.javaee.rest.serializer.JsonDateDeserializer;
import org.udg.tfg.javaee.rest.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * respuestas a enviar a través de la API REST a peticiones sobre la información
 * de los usuarios.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class ResponseUsuario {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la información básica
	 * que identifica a un usuario.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseInformacionBasicaUsuario {

		/**
		 * Identificador del usuario.
		 */
		public Long id;

		/**
		 * Nick del usuario.
		 */
		public String username;

		/**
		 * Correo electrónico del usuario.
		 */
		public String email;

		/**
		 * Constructor de la respuesta a enviar a una petición de la información básica
		 * que identifica a un usuario.
		 * 
		 * @param u
		 *            Contiene la latitud del usuario.
		 * @return Devuelve la información básica del usuario.
		 */
		public ResponseInformacionBasicaUsuario(Usuario u) {
			this.id = u.getId();
			this.username = u.getUsername();
			this.email = u.getEmail();
		}
	}

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la información del
	 * perfil de un usuario.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseInformacionUsuario {

		/**
		 * Identificador del usuario.
		 */
		public Long id;

		/**
		 * Nick del usuario.
		 */
		public String username;

		/**
		 * Nombre del usuario.
		 */
		public String nombre;

		/**
		 * Apellidos del usuario.
		 */
		public String apellidos;

		/**
		 * Correo electrónico del usuario.
		 */
		public String email;

		/**
		 * Fecha de registro del usuario.
		 */
		@JsonSerialize(using = JsonDateSerializer.class)
		@JsonDeserialize(as = JsonDateDeserializer.class)
		public Date fechaRegistro;

		/**
		 * Municipio del usuario.
		 */
		public Municipio municipio;

		/**
		 * Provincia del usuario.
		 */
		public Provincia provincia;

		/**
		 * Pais del usuario.
		 */
		public Pais pais;

		/**
		 * Listado de identificadores de las categorías deportivas favoritas del
		 * usuario.
		 */
		public List<Deporte> deportesFavoritos;

		/**
		 * Constructor de la respuesta a enviar a una petición de la información del
		 * perfil de un usuario.
		 * 
		 * @param u
		 *            Contiene la latitud del usuario.
		 * @return Devuelve la información de perfil del usuario.
		 */
		public ResponseInformacionUsuario(Usuario u) {
			this.id = u.getId();
			this.username = u.getUsername();
			this.nombre = u.getNombre();
			this.apellidos = u.getApellidos();
			this.email = u.getEmail();
			this.fechaRegistro = u.getFechaRegistro();
			this.municipio = u.getMunicipio();
			this.provincia = u.getMunicipio().getProvincia();
			this.pais = u.getMunicipio().getProvincia().getPais();
			this.deportesFavoritos = u.getDeportesFavoritos();
		}

	}

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * responder a una petición en la API REST para obtener la información de los
	 * participantes de un evento deportivo.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class ResponseInformacionParticipante {

		/**
		 * Identificador del usuario.
		 */
		public Long id;

		/**
		 * Nick del usuario.
		 */
		public String username;

		/**
		 * Municipio del usuario.
		 */
		public String municipio;

		/**
		 * Constructor de la respuesta a enviar a una petición de la información de los
		 * participantes de un evento deportivo.
		 * 
		 * @param u
		 *            Contiene la latitud del usuario.
		 * @return Devuelve la información de los participantes de un evento deportivo.
		 */
		public ResponseInformacionParticipante(Usuario u) {
			this.id = u.getId();
			this.username = u.getUsername();
			this.municipio = u.getMunicipio().getMunicipio();
		}

	}

}
