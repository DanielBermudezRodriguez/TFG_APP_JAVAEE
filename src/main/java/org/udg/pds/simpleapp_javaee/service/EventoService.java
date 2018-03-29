package org.udg.pds.simpleapp_javaee.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

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

	@Inject
	private Logger log;

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
		Foro foro = new Foro(datosEvento.esPublico, datosEvento.tituloForo);
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

	@SuppressWarnings("unchecked")
	public List<Evento> obtenerEventosNoFinalizados() {
		List<Evento> eventos = new ArrayList<>();
		Query q = em.createNamedQuery("@HQL_GET_EVENTOS_NO_FINALIZADOS");
		q.setParameter("fechaActual", new Date(), TemporalType.DATE);
		q.setParameter("idCancelado", Global.EVENTO_FINALIZADO);
		try {
			eventos = (List<Evento>) q.getResultList();
			return eventos;
		} catch (Exception e) {
			return eventos;
		}
	}

	public void finalizarEvento(Evento evento) {
		evento.setEstado(em.find(Estado.class, Global.EVENTO_FINALIZADO));
		em.persist(evento);
	}

	@SuppressWarnings("unchecked")
	public List<Evento> buscadorEventos(Long idUsuario, int limite, int offset, String titulo, List<Long> deportes,
			Date fechaEvento, int distancia) {
		Query consulta;
		String consultaString = "select e from Evento e where e.administrador.id <> :idUsuario ";
		consultaString += "and e.estado.id <> :idCancelado and e.estado.id <> :idFinalizado ";

		// Construcción consulta
		if (titulo != null && !titulo.isEmpty()) {
			consultaString += "and e.titulo like :titulo ";
		}

		if (deportes != null && !deportes.isEmpty()) {
			for (int i = 0; i < deportes.size(); i++) {
				if (i == 0)
					consultaString += "and (e.deporte.id = :deporte" + i + " ";
				else {
					consultaString += "or e.deporte.id = :deporte" + i + " ";
				}
			}
			consultaString += ") ";
		}

		if (fechaEvento != null) {
			consultaString += "and year(e.fechaEvento) = :year and month(e.fechaEvento) = :month and day(e.fechaEvento) = :day ";
		}

		// Crear consulta
		consulta = em.createQuery(consultaString);
		consulta.setFirstResult(offset);// Posición del primer resultado
		consulta.setMaxResults(limite);// Límite de resultados
		consulta.setParameter("idUsuario", idUsuario);
		consulta.setParameter("idCancelado", Global.EVENTO_SUSPENDIDO);
		consulta.setParameter("idFinalizado", Global.EVENTO_FINALIZADO);

		// Parámetros consulta
		if (titulo != null && !titulo.isEmpty()) {
			consulta.setParameter("titulo", "%" + titulo + "%");
		}

		if (deportes != null && !deportes.isEmpty()) {
			for (int i = 0; i < deportes.size(); i++) {
				consulta.setParameter("deporte" + i, deportes.get(i));
			}
		}

		if (fechaEvento != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fechaEvento);
			consulta.setParameter("year", cal.get(Calendar.YEAR));
			consulta.setParameter("month", cal.get(Calendar.MONTH) + 1);
			consulta.setParameter("day", cal.get(Calendar.DAY_OF_MONTH));
		}

		log.log(Level.INFO, consultaString);

		List<Evento> eventos = consulta.getResultList();
		return eventos;
	}

}
