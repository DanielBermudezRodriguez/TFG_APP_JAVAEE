package org.udg.pds.simpleapp_javaee.util;

public class Global {

	public static final String AUTH_ID = "simpleapp_auth_id";

	public static final Long EVENTO_ABIERTO = 1L;
	public static final Long EVENTO_COMPLETO = 2L;
	public static final Long EVENTO_SUSPENDIDO = 3L;
	public static final Long EVENTO_FINALIZADO = 4L;
	public static final double KM_DISTANCIA = 6371;
	public static final String FORMULA_DISTANCIA_GPS = " ( :km * acos(cos(radians(:latitud) ) * cos(radians( e.ubicacionGPS.latitud ) ) * cos(radians( e.ubicacionGPS.longitud ) - radians(:longitud) ) + sin (radians(:latitud) ) * sin(radians( e.ubicacionGPS.latitud )))) ";
	public static final String FORMULA_DISTANCIA_GPS_ESTIMADA = " ( :km * acos ( cos ( radians(:latitud) ) * cos( radians( e.municipio.latitudEstimada ) ) * cos( radians( e.municipio.longitudEstimada ) - radians(:longitud) ) + sin ( radians(:latitud) ) * sin( radians( e.municipio.latitudEstimada )))) ";
	public static final String DATABASE_URL_FIREBASE = "https://tfg-geinf.firebaseio.com";
	public static final String URL_FIREBASE_GOOGLE = "https://fcm.googleapis.com/fcm/send";
	public static final String API_KEY_FIREBASE = "AIzaSyDXYe8_TqlAhOnxaWXoIeBfOYgUQBbIkk0";

	public static final String NOTIFICACION_EVENTO_CANCELADO = "0";
}
