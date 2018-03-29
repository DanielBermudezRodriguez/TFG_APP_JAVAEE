package org.udg.pds.simpleapp_javaee.util;

public class Global {

	public static final String AUTH_ID = "simpleapp_auth_id";

	public static final Long EVENTO_ABIERTO = 1L;
	public static final Long EVENTO_COMPLETO = 2L;
	public static final Long EVENTO_SUSPENDIDO = 3L;
	public static final Long EVENTO_FINALIZADO = 4L;
	public static final String FORMULA_DISTANCIA_GPS = " (6371 * acos ( cos ( radians(:latitud) ) * cos( radians( e.ubicacionGPS.latitud ) ) * cos( radians( e.ubicacionGPS.longitud ) - radians(:longitud) ) + sin ( radians(:latitud) ) * sin( radians( e.ubicacionGPS.latitud )))) ";
	public static final String FORMULA_DISTANCIA_GPS_ESTIMADA = " (6371 * acos ( cos ( radians(:latitud) ) * cos( radians( e.municipio.latitudEstimada ) ) * cos( radians( e.municipio.longitudEstimada ) - radians(:longitud) ) + sin ( radians(:latitud) ) * sin( radians( e.municipio.latitudEstimada )))) ";

}
