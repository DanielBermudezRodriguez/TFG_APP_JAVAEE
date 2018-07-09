package org.udg.pds.simpleapp_javaee.util.notificaciones;

import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Usuario;

public class NotificacionUsuarioEvento {
	
	private Evento evento;
	
	private Usuario usuario;
	
	public NotificacionUsuarioEvento() {}
	
	
	public NotificacionUsuarioEvento(Evento evento, Usuario usuario) {
		this.evento = evento;
		this.usuario = usuario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
