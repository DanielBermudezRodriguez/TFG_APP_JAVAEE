package org.udg.pds.simpleapp_javaee.service;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Ubicacion;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import request.RequestUbicacion.RequestUbicacionUsuario;

@Stateless
@LocalBean
public class UbicacionService {
	
	@PersistenceContext
	private EntityManager em;

	public Ubicacion guardarUbicacionGPS(RequestUbicacionUsuario datosUbicacion, Long idUsuario) {
		
		
		
		Query q = em.createNamedQuery("@HQL_GET_MUNICIPIO_BYNAME");
		q.setParameter("municipio",datosUbicacion.municipio.toLowerCase());
		Municipio municipio;
		try {
			municipio = (Municipio) q.getSingleResult();
		} catch (Exception e) {
			throw new EJBException("No existe el municipio indicado");
		}
		
		Usuario usuario  = em.find(Usuario.class, idUsuario);
		if (usuario != null) {
			Ubicacion ubicacion = new Ubicacion(datosUbicacion.latitud,datosUbicacion.longitud,datosUbicacion.direccion,municipio);
			usuario.setUbicacionGPS(ubicacion);
			em.persist(ubicacion);
			return ubicacion;
		}
		else throw new EJBException("El usuario no está registrado");
	}

}
