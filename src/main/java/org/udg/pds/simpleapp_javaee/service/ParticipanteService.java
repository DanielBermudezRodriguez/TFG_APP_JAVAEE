package org.udg.pds.simpleapp_javaee.service;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.udg.pds.simpleapp_javaee.model.Estado;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.Global;

@Stateless
@LocalBean
public class ParticipanteService {

	@PersistenceContext
	private EntityManager em;

	public Evento addParticipanteEvento(Long idUsuario, Long idEvento) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			Estado estadoEvento = evento.getEstado();
			if (estadoEvento.getId().equals(Global.EVENTO_FINALIZADO))
				throw new EJBException("El evento ya ha finalizado");
			else if (estadoEvento.getId().equals(Global.EVENTO_SUSPENDIDO))
				throw new EJBException("El evento está suspendido");
			else if (estadoEvento.getId().equals(Global.EVENTO_COMPLETO))
				throw new EJBException("El evento ya está completo");
			else {
				Usuario usuario = em.find(Usuario.class, idUsuario);
				if (usuario != null) {
					if (!usuario.getEventosRegistrado().contains(evento)) {
						usuario.addEventosRegistrado(evento);
						evento.addParticipantes(usuario);
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
			Estado estadoEvento = evento.getEstado();
			if (estadoEvento.getId().equals(Global.EVENTO_FINALIZADO))
				throw new EJBException("El evento ya ha finalizado");
			else if (estadoEvento.getId().equals(Global.EVENTO_SUSPENDIDO))
				throw new EJBException("El evento está suspendido");
			else {
				Usuario usuario = em.find(Usuario.class, idUsuario);
				if (usuario != null) {
					// Un mismo usuario se da de baja o el administrador
					if (idUsuarioLogeado.equals(idUsuario)
							|| idUsuarioLogeado.equals(evento.getAdministrador().getId())) {
						if (usuario.getEventosRegistrado().contains(evento)) {
							usuario.getEventosRegistrado().remove(evento);
							evento.getParticipantes().remove(usuario);
							if (evento.getNumeroParticipantes() > evento.getParticipantes().size()) {
								evento.setEstado(em.find(Estado.class, Global.EVENTO_ABIERTO));
							}
							return evento;
						} else
							throw new EJBException("El usuario no está registrado en el evento");
					} else
						throw new EJBException("No puede desregistrar otros usuarios de un evento");

				} else
					throw new EJBException("El usuario no existe");
			}
		} else
			throw new EJBException("El evento no existe");
	}

}
