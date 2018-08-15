package org.udg.tfg.javaee.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.udg.tfg.javaee.model.Deporte;

@Stateless
@LocalBean
public class DeporteService {

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Deporte> obtenerDeportes() {
		List<Deporte> deportes = new ArrayList<>();
		try {
			Query q = em.createQuery("from Deporte");
			deportes = q.getResultList();
			if (deportes == null || deportes.isEmpty())
				throw new EJBException("No hay deportes disponibles");
			else
				return deportes;
		} catch (Exception e) {
			throw new EJBException("No hay deportes disponibles");
		}
	}

}
