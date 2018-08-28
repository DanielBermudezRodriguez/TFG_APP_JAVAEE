package org.udg.tfg.javaee.request;

import javax.validation.constraints.NotNull;

/**
 * Clase encargada de almacenar las clases estáticas destinadas a mapear las
 * peticiones recibidas a traves de la API REST sobre la configuración de las
 * notificaciones de un usuario.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class RequestNotificacion {

	/**
	 * Clase estática encargada de definir los atributos disponibles a la hora de
	 * recibir una petición a la API REST para configurar las notificaciones.
	 * 
	 * @author: Daniel Bermudez Rodriguez
	 * @version: 1.0
	 */
	public static class RequestModificarNotificacion {

		/**
		 * Activa o desactiva las notificaciones cuando un usuario se registra en un
		 * evento deportivo.
		 */
		@NotNull
		public Boolean altaUsuario;

		/**
		 * Activa o desactiva las notificaciones cuando un usuario se da de baja de un
		 * evento deportivo.
		 */
		@NotNull
		public Boolean bajaUsuario;

		/**
		 * Activa o desactiva las notificaciones cuando un usuario se cancela un evento
		 * deportivo.
		 */
		@NotNull
		public Boolean eventoCancelado;

		/**
		 * Activa o desactiva las notificaciones cuando un usuario modifica los datos de
		 * un evento deportivo.
		 */
		@NotNull
		public Boolean datosModificados;

		/**
		 * Activa o desactiva las notificaciones cuando un usuario elimina a un
		 * participante de un evento deportivo.
		 */
		@NotNull
		public Boolean usuarioEliminado;
	}

}
