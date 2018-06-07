package org.udg.pds.simpleapp_javaee.service;

import java.io.File;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Estado;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Foro;
import org.udg.pds.simpleapp_javaee.model.Imagen;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Ubicacion;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.util.Global;
import org.udg.pds.simpleapp_javaee.util.notificaciones.EventoCancelado;

import request.RequestEvento.RequestCrearEvento;
import response.ResponseEvento;
import response.ResponseEvento.ResponseEventoInformacion;

@Stateless
@LocalBean
public class EventoService {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Logger log;

	@Inject
	@EventoCancelado
	private Event<Evento> eventoCancelado;

	public Evento crearEventoDeportivo(RequestCrearEvento datosEvento, Long idUsuario) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date fecha = null;
		if (datosEvento.fechaEvento != null && !datosEvento.fechaEvento.isEmpty())
			try {
				fecha = sdf.parse(datosEvento.fechaEvento);
			} catch (ParseException e1) {
				throw new EJBException("Error en la creación del evento");
			}
		// crear evento datos básicos
		Evento evento = new Evento(datosEvento.titulo, datosEvento.descripcion, datosEvento.duracion,
				datosEvento.numeroParticipantes, fecha, (datosEvento.numeroParticipantes == 1) ? em.find(Estado.class, Global.EVENTO_COMPLETO) :em.find(Estado.class, Global.EVENTO_ABIERTO));

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

		// Cargamos imagen por defecto a la hora de crear un evento
		Imagen imagenPorDefecto = new Imagen(Global.NO_IMAGEN_PERFIL);
		em.persist(imagenPorDefecto);
		evento.setImagen(imagenPorDefecto);

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
					evento.getParticipantes().size();
					eventoCancelado.fire(evento);
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
	public List<ResponseEvento.ResponseEventoInformacion> buscadorEventos(Long idUsuario, int limite, int offset,
			String titulo, List<Long> deportes, Date fechaEvento, Integer distancia, Long municipio) {

		Usuario usuario = em.find(Usuario.class, idUsuario);
		Query consulta;
		String consultaString = "select e , e.participantes.size from Evento e where e.administrador.id <> :idUsuario ";
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

		// Si se escoje en el buscador una distancia máxima a buscar en función de la
		// ubicación GPS actual del usuario:
		// 1. Se coje la latitud y longitud últimas registradas del usuario actual.
		// 2. En caso de no tener ubicación GPS registrada, se escoje la latitud y
		// longitud estimadas de su municipio seleccionado al crear la cuenta.
		double latitud = 0;
		double longitud = 0;
		if (distancia != null && distancia >= 0) {
			if (distancia != 0) {
				if (usuario.getUbicacionGPS() != null) {
					latitud = usuario.getUbicacionGPS().getLatitud();
					longitud = usuario.getUbicacionGPS().getLongitud();
				} else {
					if (usuario.getMunicipio() != null) {
						latitud = usuario.getMunicipio().getLatitudEstimada();
						longitud = usuario.getMunicipio().getLongitudEstimada();
					}
				}
				consultaString += "and ((e.ubicacionGPS is not null and " + Global.FORMULA_DISTANCIA_GPS
						+ " <= :distancia) or (e.ubicacionGPS is null and e.municipio is not null and "
						+ Global.FORMULA_DISTANCIA_GPS_ESTIMADA + " <= :distancia )) ";
			}
		}
		// Escojer eventos por municipio seleccionado
		else if (municipio != null && municipio != -1) {
			consultaString += "and e.municipio.id = :municipio ";
		}

		// Ordenamos los resultados por fecha del evento
		consultaString += " order by e.fechaEvento ";

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

		if (distancia > 0) {
			consulta.setParameter("latitud", latitud);
			consulta.setParameter("longitud", longitud);
			consulta.setParameter("distancia", distancia.doubleValue());
			consulta.setParameter("km", Global.KM_DISTANCIA);
		}

		if (municipio != null && municipio != -1) {
			consulta.setParameter("municipio", municipio);
		}

		log.log(Level.INFO, consultaString);

		List<Object> resultados = consulta.getResultList();

		List<ResponseEvento.ResponseEventoInformacion> responseEventos = new ArrayList<>();
		for (int i = 0; i < resultados.size(); i++) {
			Object[] resultado = (Object[]) resultados.get(i);
			responseEventos
					.add(new ResponseEvento.ResponseEventoInformacion((Evento) resultado[0], (Integer) resultado[1]));
		}

		return responseEventos;
	}

	public ResponseEventoInformacion obtenerEvento(Long idEvento) {
		Evento e = em.find(Evento.class, idEvento);
		if (e != null) {
			return new ResponseEvento.ResponseEventoInformacion(e, e.getParticipantes().size());
		} else
			throw new EJBException("El evento solicitado no existe");

	}

	public Imagen guardarImagenEvento(Long idEvento, List<String> imagenSubida, Path baseDir) {
		Evento e = em.find(Evento.class, idEvento);
		if (e != null) {
			if (imagenSubida != null && !imagenSubida.isEmpty()) {
				Imagen imagen = e.getImagen();
				eliminarImagenEvento(imagen, baseDir);
				imagen.setRuta(imagenSubida.get(0));
				return imagen;
			} else
				throw new EJBException("No se puede subir la imagen");
		} else
			throw new EJBException("El usuario no existe");
	}

	private void eliminarImagenEvento(Imagen imagen, Path baseDir) {
		if (!imagen.getRuta().equals(Global.NO_IMAGEN_PERFIL)) {
			File file = new File(baseDir.toString() + "\\" + imagen.getRuta());
			if (file.delete())
				log.log(Level.INFO, "Imagen eliminada correctamente");
			else
				log.log(Level.INFO, "No se pudo eliminar la imagen");
		}
	}

	public String obtenerImagen(Long idEvento) {
		if (idEvento.equals(0L)) {
			return Global.NO_IMAGEN_PERFIL;
		} else {
			Evento e = em.find(Evento.class, idEvento);
			if (e != null) {
				return e.getImagen().getRuta();
			} else
				throw new EJBException("El usuario no existe");
		}

	}

}
