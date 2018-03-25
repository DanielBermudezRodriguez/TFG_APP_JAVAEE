package org.udg.pds.simpleapp_javaee.service;

import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Foro;
import org.udg.pds.simpleapp_javaee.model.Mensaje;
import org.udg.pds.simpleapp_javaee.model.Usuario;

import request.RequestForo.RequestMensajeForo;

@Stateless
@LocalBean
public class ForoService {

	@PersistenceContext
	private EntityManager em;

	public Mensaje enviarMensajeForo(RequestMensajeForo mensajeForo, Long idUsuario) {
		Foro foro = em.find(Foro.class, mensajeForo.idForo);
		if (foro != null) {
			Evento evento = foro.getEvento();
			Usuario usuario = em.find(Usuario.class, idUsuario);
			// Foro es publico o si no es publico el usuario está registrado o es el
			// administrador
			if ((foro.getEsPublico()) || (!foro.getEsPublico()
					&& (evento.getParticipantes().contains(usuario) || usuario.getEventosCreados().contains(evento)))) {
				Mensaje mensaje = new Mensaje(mensajeForo.mensaje, new Date());
				foro.addMensaje(mensaje);
				mensaje.setUsuario(usuario);
				mensaje.setForo(foro);
				em.persist(mensaje);
				return mensaje;
			} else
				throw new EJBException("No puede enviar mensajes en este foro");
		} else
			throw new EJBException("El foro no existe");
	}

	public Foro obtenerForo(Long idEvento, Long idUsuario) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			Foro foro = em.find(Foro.class, evento.getForo().getId());
			if (foro != null) {
				Usuario usuario = em.find(Usuario.class, idUsuario);
				if (usuario != null) {
					if ((foro.getEsPublico()) || (!foro.getEsPublico() && (evento.getParticipantes().contains(usuario)
							|| usuario.getEventosCreados().contains(evento)))) {
						foro.getMensajes().size();
						return foro;
					} else
						throw new EJBException("No puede visualizar el contenido del foro");
				} else
					throw new EJBException("El usuario no existe");
			} else
				throw new EJBException("El foro no existe");
		} else
			throw new EJBException("El evento no existe");
	}

}
