package org.udg.pds.simpleapp_javaee.service;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Estado;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Foro;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Ubicacion;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.Global;

import request.RequestEvento.RequestCrearEvento;

@Stateless
@LocalBean
public class EventoService {

	@PersistenceContext
	private EntityManager em;

	@Schedule(second = "*/20", minute = "*", hour = "*", persistent = false)
	public void doWork() {
		Query q = em.createNamedQuery("@HQL_GET_EVENTOS_NO_FINALIZADOS");
		q.setParameter("idEstado", Global.EVENTO_SUSPENDIDO);
		try {
			List<Evento> eventos = (List<Evento>) q.getResultList();
			System.out.println("total: " + eventos.size());
		} catch (Exception e) {
			System.out.println("No hay resultado");
		}
	}

	public Evento crearEventoDeportivo(RequestCrearEvento datosEvento, Long idUsuario) {
		// crear evento datos básicos
		Evento evento = new Evento(datosEvento.titulo, datosEvento.descripcion, datosEvento.duracion,
				datosEvento.numeroParticipantes, datosEvento.fechaEvento, em.find(Estado.class, Global.EVENTO_ABIERTO));

		// Evento creado por municipio seleccionada o ubicación GPS seleccionada
		if (datosEvento.municipio != null) {
			Municipio municipio = em.find(Municipio.class, datosEvento.municipio);
			if (municipio != null) {
				municipio.addEvento(evento);
				evento.setMunicipio(municipio);
			} else
				throw new EJBException("El municipio no existe");
		} else if (datosEvento.ubicacionGPS != null) {
			Query q = em.createNamedQuery("@HQL_GET_MUNICIPIO_BYNAME");
			q.setParameter("municipio", datosEvento.ubicacionGPS.municipio.toLowerCase());
			try {
				Municipio municipio = (Municipio) q.getSingleResult();
				municipio.addEvento(evento);
				evento.setMunicipio(municipio);
				Ubicacion u = new Ubicacion(datosEvento.ubicacionGPS.latitud, datosEvento.ubicacionGPS.longitud,
						datosEvento.ubicacionGPS.direccion, municipio);
				evento.setUbicacionGPS(u);
				em.persist(u);
			} catch (Exception e) {
				throw new EJBException("No existe el municipio");
			}
		}

		// Inicializar el foro
		Foro foro = new Foro(datosEvento.esPublico);
		evento.setForo(foro);
		foro.setEvento(evento);
		em.persist(foro);

		Usuario usuario = (em.find(Usuario.class, idUsuario));
		if (usuario != null) {
			evento.setAdministrador(usuario);
			evento.addParticipantes(usuario);
			usuario.addEventosCreados(evento);
			usuario.addEventosRegistrado(evento);
		} else
			throw new EJBException("El usuario no existe");

		Deporte deporte = em.find(Deporte.class, datosEvento.deporte);
		if (deporte != null) {
			evento.setDeporte(deporte);
			deporte.addEventoDeportivo(evento);
		} else
			throw new EJBException("El deporte indicado no existe");

		em.persist(evento);
		return evento;

	}

	public Evento cancelarEvento(Long idUsuario, Long idEvento) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			if (evento.getEstado().getId().equals(Global.EVENTO_FINALIZADO))
				throw new EJBException("El evento ya ha finalizado");
			else if (evento.getEstado().getId().equals(Global.EVENTO_SUSPENDIDO))
				throw new EJBException("El evento ya ha sido suspendido");
			else {
				if (evento.getAdministrador().getId().equals(idUsuario)) {
					evento.setEstado(em.find(Estado.class, Global.EVENTO_SUSPENDIDO));
					return evento;
				} else
					throw new EJBException("No puede cancelar el evento");
			}
		} else
			throw new EJBException("El evento no existe");
	}

}
