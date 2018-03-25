package org.udg.pds.simpleapp_javaee.service;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Ubicacion;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import request.RequestUbicacion.RequestUbicacionUsuario;
import response.ResponseUbicacion.ResponseUbicacionEvento;

@Stateless
@LocalBean
public class UbicacionService {

	@PersistenceContext
	private EntityManager em;

	public Ubicacion guardarUbicacionGPS(RequestUbicacionUsuario datosUbicacion, Long idUsuario) {

		Query q = em.createNamedQuery("@HQL_GET_MUNICIPIO_BYNAME");
		q.setParameter("municipio", datosUbicacion.municipio.toLowerCase());
		Municipio municipio;
		try {
			municipio = (Municipio) q.getSingleResult();
		} catch (Exception e) {
			throw new EJBException("No existe el municipio indicado");
		}

		Usuario usuario = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			Ubicacion ubicacion = new Ubicacion(datosUbicacion.latitud, datosUbicacion.longitud,
					datosUbicacion.direccion, municipio);
			usuario.setUbicacionGPS(ubicacion);
			em.persist(ubicacion);
			return ubicacion;
		} else
			throw new EJBException("El usuario no está registrado");
	}

	public ResponseUbicacionEvento ubicacionEvento(Long idEvento) {
		Evento evento = em.find(Evento.class, idEvento);
		if (evento != null) {
			if (evento.getUbicacionGPS() != null)
				return new ResponseUbicacionEvento(evento.getUbicacionGPS().getLatitud(),
						evento.getUbicacionGPS().getLongitud());
			else if (evento.getMunicipio() != null)
				return new ResponseUbicacionEvento(evento.getMunicipio().getLatitudEstimada(),
						evento.getMunicipio().getLongitudEstimada());
			else
				throw new EJBException("No hay ubicacion para el evento");
		} else
			throw new EJBException("No existe el evento");
	}

}
