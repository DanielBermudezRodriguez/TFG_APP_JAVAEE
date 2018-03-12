package org.udg.pds.simpleapp_javaee.service;

import java.util.Collection;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.udg.pds.simpleapp_javaee.model.Pais;
import org.udg.pds.simpleapp_javaee.model.Task;

@Stateless
@LocalBean
public class PaisService {

    @PersistenceContext
    protected EntityManager em;
	
	@SuppressWarnings("unchecked")
	public Collection<Pais> obtenerPaises() {
		Collection<Pais> paises = null;
        try {
            Query q = em.createQuery("select p.id , p.pais from Pais p ");
            if (q.getResultList().isEmpty()) {
            	throw new EJBException("No hay países disponibles");
            }
            else {
            	paises = q.getResultList();
            } return paises;
        } catch (Exception e){
            throw new EJBException("No hay países disponibles");
        }
	}
	


}
