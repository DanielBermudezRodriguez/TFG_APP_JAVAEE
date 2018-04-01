package org.udg.pds.simpleapp_javaee.util.notificaciones;

import javax.ejb.Asynchronous;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import org.udg.pds.simpleapp_javaee.model.Evento;

public class EventoListener extends NotificacionListener {

	// De forma asíncrona
	// Varios eventos en paralelo
	@Asynchronous
	@Lock(LockType.READ)
	public void notificarEventoCancelado(@Observes(during = TransactionPhase.AFTER_SUCCESS) @EventoCancelado Evento e) {
		destinatarios.addAll(e.getParticipantes());
		contenidoNotificacion.put("title", e.getTitulo());
		contenidoNotificacion.put("message", "Cancelado!");
		enviarNotificacion();
	}

}
