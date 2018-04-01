package org.udg.pds.simpleapp_javaee.util.notificaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.udg.pds.simpleapp_javaee.model.Usuario;

public class NotificacionListener {

	protected HashMap<String, String> contenidoNotificacion = new HashMap<>();

	protected List<Usuario> destinatarios = new ArrayList<>();

	protected void enviarNotificacion() {
		Notificacion notificacion = new Notificacion();

		for (Usuario u : destinatarios) {
			notificacion.addToken(u.getTokenFireBase());
		}

		notificacion.crearNotificacion(this.contenidoNotificacion);
		EnviarNotificacion.enviarNotificacion(notificacion);
	}

}
