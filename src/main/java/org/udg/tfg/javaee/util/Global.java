package org.udg.tfg.javaee.util;

/**
 * Esta clase define las constantes utilizadas en la aplicación.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class Global {

	/**
	 * Literal utilizado para obtener la sesión del usuario actual de la clase HttpSession.
	 */
	public static final String AUTH_ID = "simpleapp_auth_id";
	/**
	 * Valor del estado de un evento abierto.
	 */
	public static final Long EVENTO_ABIERTO = 1L;
	/**
	 * Valor del estado de un evento completo.
	 */
	public static final Long EVENTO_COMPLETO = 2L;
	/**
	 * Valor del estado de un evento suspendido.
	 */
	public static final Long EVENTO_SUSPENDIDO = 3L;
	/**
	 * Valor del estado de un evento finalizado.
	 */
	public static final Long EVENTO_FINALIZADO = 4L;
	/**
	 * Valor constante en Kilómetros para calcular la distancia GPS entre dos posiciones.
	 */
	public static final double KM_DISTANCIA = 6371;
	/**
	 * Fórmula para calcular la distancia GPS entre la posición GPS de un evento deportivo y la posición GPS de un dispositivo móvil.
	 */
	public static final String FORMULA_DISTANCIA_GPS = " ( :km * acos(cos(radians(:latitud) ) * cos(radians( e.ubicacionGPS.latitud ) ) * cos(radians( e.ubicacionGPS.longitud ) - radians(:longitud) ) + sin (radians(:latitud) ) * sin(radians( e.ubicacionGPS.latitud )))) ";
	/**
	 * Fórmula para calcular la distancia GPS entre la posición GPS de un evento deportivo y la posición GPS de un municipio.
	 */
	public static final String FORMULA_DISTANCIA_GPS_ESTIMADA = " ( :km * acos ( cos ( radians(:latitud) ) * cos( radians( e.municipio.latitudEstimada ) ) * cos( radians( e.municipio.longitudEstimada ) - radians(:longitud) ) + sin ( radians(:latitud) ) * sin( radians( e.municipio.latitudEstimada )))) ";
	/**
	 * Dirección de la base de datos de Firebase.
	 */
	public static final String DATABASE_URL_FIREBASE = "https://tfg-geinf.firebaseio.com";
	/**
	 * Dirección de la API de Firebase para el manejo de envío de notificaciones.
	 */
	public static final String URL_FIREBASE_GOOGLE = "https://fcm.googleapis.com/fcm/send";
	/**
	 * Clave de la API de Firebase.
	 */
	public static final String API_KEY_FIREBASE = "AIzaSyCnV9cBkKYzkIjXh2N22-AdjEPIRb-0QdQ";
	/**
	 * Variable de entorno de las imagenes de los usuarios de la aplicación.
	 */
	public static final String VARIABLE_ENTORNO_IMAGENES_USUARIOS = "DIR_TFG_IMAGES_USERS";
	/**
	 * Variable de entorno de las imagenes de los eventos deportivos de la aplicación.
	 */
	public static final String VARIABLE_ENTORNO_IMAGENES_EVENTOS = "DIR_TFG_IMAGES_EVENTS";
	/**
	 * Tipo de notificación para los eventos cancelados.
	 */
	public static final String NOTIFICACION_EVENTO_CANCELADO = "0";
	/**
	 * Tipo de notificación para usuario desapuntado de un evento deportivo.
	 */
	public static final String NOTIFICACION_DESAPUNTADO_DEL_EVENTO = "1";
	/**
	 * Tipo de notificación para la modificación de un evento deportivo.
	 */
	public static final String NOTIFICACION_EVENTO_MODIFICADO = "2";
	/**
	 * Tipo de notificación para el registro de un nuevo usuario.
	 */
	public static final String NOTIFICACION_EVENTO_ALTA_USUARIO = "3";
	/**
	 * Tipo de notificación para cuando un usuario es eliminado de un evento por el administrador.
	 */
	public static final String NOTIFICACION_EVENTO_BAJA_USUARIO = "4";
	/**
	 * Nombre de la imagen por defecto de un usuario o evento deportivo.
	 */
	public static final String NO_IMAGEN_PERFIL = "no-image.png";

}
