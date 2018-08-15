package org.udg.tfg.javaee.notificaciones;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

import org.udg.tfg.javaee.model.Evento;
import org.udg.tfg.javaee.model.Usuario;
import org.udg.tfg.javaee.util.Global;

public class EventoListener extends NotificacionListener {

	// De forma asíncrona
	// Varios eventos en paralelo
	@Asynchronous
	@Lock(LockType.READ)
	public void notificarEventoCancelado(@Observes(during = TransactionPhase.AFTER_SUCCESS) @EventoCancelado Evento e) {
		List<Usuario> participantes = new ArrayList<>();
		for (Usuario u : e.getParticipantes()) {
			// No es el administrador y el participante tiene activadas notificaciones de
			// eventos cancelados
			if (e.getAdministrador().getId() != u.getId() && u.getNotificacion().getEventoCancelado())
				participantes.add(u);
		}
		if (participantes != null && !participantes.isEmpty()) {
			destinatarios.addAll(participantes);
			contenidoNotificacion.put("title", e.getTitulo());
			contenidoNotificacion.put("message", "El evento ha sido cancelado");
			contenidoNotificacion.put("notificationType", Global.NOTIFICACION_EVENTO_CANCELADO);
			contenidoNotificacion.put("idEvento", e.getId().toString());
			enviarNotificacion();
		}

	}

	// De forma asíncrona
	// Varios eventos en paralelo
	@Asynchronous
	@Lock(LockType.READ)
	public void notificarDesapuntadoEventoPorAdministrador(
			@Observes(during = TransactionPhase.AFTER_SUCCESS) @DesapuntadoEvento NotificacionUsuarioEvento notificacion) {
		List<Usuario> participantes = new ArrayList<>();
		// Si el usuario eliminado del evento tiene activadas las notificaciones
		if (notificacion.getUsuario().getNotificacion().getUsuarioEliminado())
			participantes.add(notificacion.getUsuario());

		if (participantes != null && !participantes.isEmpty()) {
			destinatarios.addAll(participantes);
			contenidoNotificacion.put("title", notificacion.getEvento().getTitulo());
			contenidoNotificacion.put("message", "Has sido eliminado del evento");
			contenidoNotificacion.put("notificationType", Global.NOTIFICACION_DESAPUNTADO_DEL_EVENTO);
			contenidoNotificacion.put("idEvento", notificacion.getEvento().getId().toString());
			enviarNotificacion();
		}

	}
	
	// De forma asíncrona
	// Varios eventos en paralelo
	@Asynchronous
	@Lock(LockType.READ)
	public void notificarEventoModificado(@Observes(during = TransactionPhase.AFTER_SUCCESS) @EventoModificado Evento e) {
		List<Usuario> participantes = new ArrayList<>();
		for (Usuario u : e.getParticipantes()) {
			// No es el administrador y el participante tiene activadas notificaciones de
			// eventos modificados
			if (e.getAdministrador().getId() != u.getId() && u.getNotificacion().getDatosModificados())
				participantes.add(u);
		}
		if (participantes != null && !participantes.isEmpty()) {
			destinatarios.addAll(participantes);
			contenidoNotificacion.put("title", e.getTitulo());
			contenidoNotificacion.put("message", "El evento ha sido modificado");
			contenidoNotificacion.put("notificationType", Global.NOTIFICACION_EVENTO_MODIFICADO);
			contenidoNotificacion.put("idEvento", e.getId().toString());
			enviarNotificacion();
		}

	}
	
	// De forma asíncrona
	// Varios eventos en paralelo
	@Asynchronous
	@Lock(LockType.READ)
	public void notificarAltaUsuarioEvento(
			@Observes(during = TransactionPhase.AFTER_SUCCESS) @AltaUsuario NotificacionUsuarioEvento notificacion) {
		List<Usuario> participantes = new ArrayList<>();
		// Si el usuario eliminado del evento tiene activadas las notificaciones
		if (notificacion.getEvento().getAdministrador().getNotificacion().getAltaUsuario())
			participantes.add(notificacion.getEvento().getAdministrador());

		if (participantes != null && !participantes.isEmpty()) {
			destinatarios.addAll(participantes);
			contenidoNotificacion.put("title", notificacion.getEvento().getTitulo());
			contenidoNotificacion.put("message", "El usuario " + notificacion.getUsuario().getUsername() + " se ha apuntado al evento");
			contenidoNotificacion.put("notificationType", Global.NOTIFICACION_EVENTO_ALTA_USUARIO);
			contenidoNotificacion.put("idEvento", notificacion.getEvento().getId().toString());
			enviarNotificacion();
		}

	}
	
	// De forma asíncrona
	// Varios eventos en paralelo
	@Asynchronous
	@Lock(LockType.READ)
	public void notificarBajaUsuarioEvento(
			@Observes(during = TransactionPhase.AFTER_SUCCESS) @BajaUsuario NotificacionUsuarioEvento notificacion) {
		List<Usuario> participantes = new ArrayList<>();
		// Si el usuario eliminado del evento tiene activadas las notificaciones
		if (notificacion.getEvento().getAdministrador().getNotificacion().getBajaUsuario())
			participantes.add(notificacion.getEvento().getAdministrador());

		if (participantes != null && !participantes.isEmpty()) {
			destinatarios.addAll(participantes);
			contenidoNotificacion.put("title", notificacion.getEvento().getTitulo());
			contenidoNotificacion.put("message", "El usuario " + notificacion.getUsuario().getUsername() + " se ha desapuntado del evento");
			contenidoNotificacion.put("notificationType", Global.NOTIFICACION_EVENTO_BAJA_USUARIO);
			contenidoNotificacion.put("idEvento", notificacion.getEvento().getId().toString());
			enviarNotificacion();
		}

	}

}
