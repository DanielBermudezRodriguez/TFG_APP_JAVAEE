package org.udg.pds.simpleapp_javaee.util.notificaciones;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> tokensDestinatarios;

	private Map<String, String> notificacion;

	public Notificacion() {

	}

	public void addToken(String tokenFireBase) {
		if (tokensDestinatarios == null)
			tokensDestinatarios = new LinkedList<String>();
		tokensDestinatarios.add(tokenFireBase);
	}

	public void crearNotificacion(String titol, String missatge) {
		if (notificacion == null)
			notificacion = new HashMap<String, String>();

		notificacion.put("title", titol);
		notificacion.put("message", missatge);
	}

	public void crearNotificacion(HashMap<String, String> p_data) {
		this.notificacion = p_data;
	}

	public List<String> getTokensDestinatarios() {
		return tokensDestinatarios;
	}

	public void setTokensDestinatarios(List<String> tokensDestinatarios) {
		this.tokensDestinatarios = tokensDestinatarios;
	}

	public Map<String, String> getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Map<String, String> notificacion) {
		this.notificacion = notificacion;
	}

}
