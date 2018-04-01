package org.udg.pds.simpleapp_javaee.util.notificaciones;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Notificacion implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> registration_ids;

	private Map<String, String> data;

	public Notificacion() {

	}

	public void addToken(String tokenFireBase) {
		if (registration_ids == null)
			registration_ids = new LinkedList<String>();
		registration_ids.add(tokenFireBase);
	}

	public void crearNotificacion(String titol, String missatge) {
		if (data == null)
			data = new HashMap<String, String>();

		data.put("title", titol);
		data.put("message", missatge);
	}

	public void crearNotificacion(HashMap<String, String> p_data) {
		this.data = p_data;
	}

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
