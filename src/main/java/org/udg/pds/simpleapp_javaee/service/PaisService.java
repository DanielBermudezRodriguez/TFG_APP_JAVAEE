package org.udg.pds.simpleapp_javaee.service;


import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.udg.pds.simpleapp_javaee.model.Pais;


@Stateless
@LocalBean
public class PaisService {

    @PersistenceContext
    protected EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Pais> obtenerPaises() {
		List<Pais> paises = null;
        try {
            Query q = em.createQuery("select p from Pais p ");
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
