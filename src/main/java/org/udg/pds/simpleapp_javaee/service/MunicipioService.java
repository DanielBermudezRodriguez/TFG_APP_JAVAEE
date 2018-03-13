package org.udg.pds.simpleapp_javaee.service;

import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Provincia;

@Stateless
@LocalBean
public class MunicipioService {
	
    @PersistenceContext
    protected EntityManager em;
    
	@SuppressWarnings("unchecked")
	public Collection<Municipio> obtenerMunicipiosProvincia(Long idProvincia) {
		try {
			Collection<Municipio> municipios = null;
			Provincia provincia = em.find(Provincia.class,idProvincia);
			municipios = provincia.getMunicipios();
			return municipios;
		} catch (Exception e) {
			throw new EJBException("No hay municipios disponibles");
		} 
	}

}
