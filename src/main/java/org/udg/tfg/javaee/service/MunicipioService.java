package org.udg.tfg.javaee.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.udg.tfg.javaee.model.Municipio;
import org.udg.tfg.javaee.model.Provincia;

@Stateless
@LocalBean
public class MunicipioService {

	@PersistenceContext
	protected EntityManager em;

	public List<Municipio> obtenerMunicipiosProvincia(Long idProvincia) {
		try {
			List<Municipio> municipios = null;
			Provincia provincia = em.find(Provincia.class, idProvincia);
			municipios = provincia.getMunicipios();
			return municipios;
		} catch (Exception e) {
			throw new EJBException("No hay municipios disponibles");
		}
	}

}
