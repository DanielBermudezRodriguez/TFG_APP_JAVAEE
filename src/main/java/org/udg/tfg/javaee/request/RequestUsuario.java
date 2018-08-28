package org.udg.tfg.javaee.request;

import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * peticiones recibidas a traves de la API REST sobre las acciones disponibles
 * en los usuarios.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class RequestUsuario {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para el inicio de sesión de un usuario.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestLoginUsuario {

		/**
		 * Indica el correo electrónico del usuario.
		 */
		@NotNull
		public String email;

		/**
		 * Indica la contraseña del usuario.
		 */
		@NotNull
		public String password;

		/**
		 * Indica el token de Firebase del dispositivo móvil.
		 */
		@NotNull
		public String tokenFireBase;
	}

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para el registro de un nuevo usuario en la
	 * aplicación.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestRegistroUsuario {

		/**
		 * Indica el Nick del usuario.
		 */
		@NotNull
		public String username;

		/**
		 * Indica el correo electrónico del usuario.
		 */
		@NotNull
		public String email;

		/**
		 * Indica la contraseña del usuario.
		 */
		@NotNull
		public String password;

		/**
		 * Indica el nombre del usuario.
		 */
		@NotNull
		public String nombre;

		/**
		 * Indica los apellidos del usuario.
		 */
		@NotNull
		public String apellidos;

		/**
		 * Indica el token de Firebase del dispositivo móvil.
		 */
		@NotNull
		public String tokenFireBase;

		/**
		 * Indica el identificador del municipio del usuario.
		 */
		@NotNull
		public Long municipio;

		/**
		 * Indica los identificadores de las categorías deportivas favoritas del
		 * usuario.
		 */
		@NotNull
		public List<Long> deportesFavoritos;

	}

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para modificar los datos del perfil de un
	 * usuario dado de alta en la aplicación.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestModificarUsuario {

		/**
		 * Indica el Nick del usuario.
		 */
		@NotNull
		public String username;

		/**
		 * Indica el nombre del usuario.
		 */
		@NotNull
		public String nombre;

		/**
		 * Indica los apellidos del usuario.
		 */
		@NotNull
		public String apellidos;

		/**
		 * Indica el identificador del municipio del usuario.
		 */
		@NotNull
		public Long municipio;

		/**
		 * Indica los identificadores de las categorías deportivas favoritas del
		 * usuario.
		 */
		@NotNull
		public List<Long> deportesFavoritos;

	}

}
