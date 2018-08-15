package org.udg.tfg.javaee.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.udg.tfg.javaee.model.Estado;
import org.udg.tfg.javaee.model.Evento;
import org.udg.tfg.javaee.model.Usuario;
import org.udg.tfg.javaee.notificaciones.AltaUsuario;
import org.udg.tfg.javaee.notificaciones.BajaUsuario;
import org.udg.tfg.javaee.notificaciones.DesapuntadoEvento;
import org.udg.tfg.javaee.notificaciones.NotificacionUsuarioEvento;
import org.udg.tfg.javaee.util.Global;

@Stateless
@LocalBean
public class ParticipanteService {

	@PersistenceContext
	private EntityManager em;
	
	@Inject
	@DesapuntadoEvento
	private Event<NotificacionUsuarioEvento> desapuntadoEvento;
	
	@Inject
	@AltaUsuario
	private Event<NotificacionUsuarioEvento> altaUsuarioEvento;
	
	@Inject
	@BajaUsuario
	private Event<NotificacionUsuarioEvento> bajaUsuarioEvento;

	public Evento addParticipanteEvento(Long idUsuario, Long idEvento) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			Estado estadoEvento = evento.getEstado();
			if (estadoEvento.getId().equals(Global.EVENTO_FINALIZADO))
				throw new EJBException("El evento ya ha finalizado");
			else if (estadoEvento.getId().equals(Global.EVENTO_SUSPENDIDO))
				throw new EJBException("El evento está suspendido");
			else if (estadoEvento.getId().equals(Global.EVENTO_COMPLETO)) {
				Usuario usuario = em.find(Usuario.class, idUsuario);
				if (usuario != null) {
					if (!usuario.getEventosEnCola().contains(evento)) {
						usuario.addEventosEnCola(evento);
						evento.addParticipantesEnCola(usuario);
						/*if (!evento.getAdministrador().getId().equals(idUsuario)) {
							altaUsuarioEvento.fire(new NotificacionUsuarioEvento(evento, usuario));
						}*/
						/*if (evento.getNumeroParticipantes() == evento.getParticipantes().size()) {
							evento.setEstado(em.find(Estado.class, Global.EVENTO_COMPLETO));
						}*/
						return evento;
					} else
						throw new EJBException("Ya está en la cola del evento");

				} else
					throw new EJBException("El usuario no existe");
				//throw new EJBException("El evento ya está completo");	
			}
			else {
				Usuario usuario = em.find(Usuario.class, idUsuario);
				if (usuario != null) {
					if (!usuario.getEventosRegistrado().contains(evento)) {
						usuario.addEventosRegistrado(evento);
						evento.addParticipantes(usuario);
						if (!evento.getAdministrador().getId().equals(idUsuario)) {
							altaUsuarioEvento.fire(new NotificacionUsuarioEvento(evento, usuario));
						}
						if (evento.getNumeroParticipantes() == evento.getParticipantes().size()) {
							evento.setEstado(em.find(Estado.class, Global.EVENTO_COMPLETO));
						}
						return evento;
					} else
						throw new EJBException("Ya está registrado en el evento");

				} else
					throw new EJBException("El usuario no existe");
			}
		} else
			throw new EJBException("El evento no existe");
	}

	public Evento eliminarParticipanteEvento(Long idUsuario, Long idEvento, Long idUsuarioLogeado) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			Usuario usuario = em.find(Usuario.class, idUsuario);
			if (usuario != null) {
				// Un mismo usuario se da de baja o el administrador
				if (idUsuarioLogeado.equals(idUsuario) || idUsuarioLogeado.equals(evento.getAdministrador().getId())) {
					if (usuario.getEventosRegistrado().contains(evento)) {
						usuario.getEventosRegistrado().remove(evento);
						evento.getParticipantes().remove(usuario);
						// Administrador del evento da de baja a un usuario que no es el mismo lanzamos una notificación al usuario afectado
						if (idUsuarioLogeado.equals(evento.getAdministrador().getId()) && idUsuarioLogeado != idUsuario ) {
							desapuntadoEvento.fire(new NotificacionUsuarioEvento(evento,usuario));							
						}
						// Propio participante se da de baja de un evento
						else if (!idUsuarioLogeado.equals(evento.getAdministrador().getId())) {
							bajaUsuarioEvento.fire(new NotificacionUsuarioEvento(evento,usuario));
						}
						if (evento.getNumeroParticipantes() > evento.getParticipantes().size()) {
							evento.setEstado(em.find(Estado.class, Global.EVENTO_ABIERTO));
							// Dar de alta al primer usuario de la cola
							if (evento.getParticipantesEnCola() != null && evento.getParticipantesEnCola().size() > 0) {
								Usuario usuarioEnCola = evento.getParticipantesEnCola().get(0);
								usuarioEnCola.getEventosEnCola().remove(evento);
								evento.getParticipantesEnCola().remove(usuarioEnCola);
								usuarioEnCola.addEventosRegistrado(evento);
								evento.addParticipantes(usuarioEnCola);
								// Notificar alta al administrador del evento nuevo usuario añadido de la cola
								if (!evento.getAdministrador().getId().equals(idUsuario)) {
									altaUsuarioEvento.fire(new NotificacionUsuarioEvento(evento, usuario));
								}
								if (evento.getNumeroParticipantes() != 0 && evento.getNumeroParticipantes() == evento.getParticipantes().size()) {
									evento.setEstado(em.find(Estado.class, Global.EVENTO_COMPLETO));
								}
								
							}
						}
						return evento;
					}
					// Dar de baja a un usuario de la cola de espera en un evento completo
					else if (usuario.getEventosEnCola().contains(evento)) {
						usuario.getEventosEnCola().remove(evento);
						evento.getParticipantesEnCola().remove(usuario);
						return evento;
					}
					else
						throw new EJBException("No participas en el evento");
				} else
					throw new EJBException("No puede desregistrar otros usuarios de un evento");

			} else
				throw new EJBException("El usuario no existe");
		} else
			throw new EJBException("El evento no existe");
	}

	public List<Usuario> obtenerParticipantesEvento(Long idEvento, Long obtenerUsuarioLogeado, Long tipo) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			List<Usuario> participantes = new ArrayList<>();
			if (tipo.equals(0L))
				participantes = evento.getParticipantes();
			else if (tipo.equals(1L))
				participantes = evento.getParticipantesEnCola();
			else if (tipo.equals(2L)) {
				participantes.addAll(evento.getParticipantes());
				participantes.addAll(evento.getParticipantesEnCola());
			}
			return participantes;
		} else
			throw new EJBException("El evento no existe");
	}
	

}
