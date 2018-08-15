package org.udg.tfg.javaee.service;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.udg.tfg.javaee.model.Pais;
import org.udg.tfg.javaee.model.Provincia;

@Stateless
@LocalBean
public class ProvinciaService {

	@PersistenceContext
	protected EntityManager em;

	public List<Provincia> obtenerProvinciasPais(Long idPais) {
		try {
			List<Provincia> provincias = null;
			Pais pais = em.find(Pais.class, idPais);
			provincias = pais.getProvincias();
			return provincias;
		} catch (Exception e) {
			throw new EJBException("No hay provincias disponibles");
		}
	}

}
