package org.udg.pds.simpleapp_javaee.util;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class InitDB {

	@Inject
	private Logger log;

	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	private void init() {
		log.log(Level.INFO, "Inicialización de registros en la base de datos...");

		/*
		 * try { Usuario exists = em.find(Usuario.class, 1L); if (exists == null) {
		 * Usuario u = new Usuario("jo", "jo@hotmail.com",
		 * "jo","Daniel","Bermudez Rodriguez","646094314"); em.persist(u); Task t1 = new
		 * Task(new Date(), new Date(), false, "Tarea número 1"); u.addTask(t1);
		 * t1.setUser(u);
		 * 
		 * Usuario u2 = new Usuario("tu", "tu@hotmail.com",
		 * "tu","Sergio","Bermudez Rodriguez","629725139"); em.persist(u2); Task t2 =
		 * new Task(new Date(), new Date(), false, "Tarea número 2"); u2.addTask(t2);
		 * t2.setUser(u2);
		 * 
		 * // Pais Pais pais = new Pais("España"); em.persist(pais);
		 * 
		 * // Provincias Provincia provincia = new Provincia("Gerona");
		 * pais.addProvincias(provincia); provincia.setPais(pais);
		 * 
		 * Provincia provincia2 = new Provincia("Tarragona");
		 * pais.addProvincias(provincia2); provincia2.setPais(pais);
		 * 
		 * // Municipios Gerona Municipio municipio = new Municipio("Lloret de Mar");
		 * provincia.addMunicipios(municipio); municipio.setProvincia(provincia);
		 * 
		 * Municipio municipio2 = new Municipio("Blanes");
		 * provincia.addMunicipios(municipio2); municipio2.setProvincia(provincia);
		 * 
		 * Municipio municipio3 = new Municipio("Tossa de Mar");
		 * provincia.addMunicipios(municipio3); municipio3.setProvincia(provincia);
		 * 
		 * // Municipios Tarragona Municipio municipio4 = new Municipio("Alcanar");
		 * provincia2.addMunicipios(municipio4); municipio4.setProvincia(provincia2);
		 * 
		 * Municipio municipio5 = new Municipio("Blancafort");
		 * provincia2.addMunicipios(municipio5); municipio5.setProvincia(provincia2);
		 * 
		 * Municipio municipio6 = new Municipio("Calafell");
		 * provincia2.addMunicipios(municipio6); municipio6.setProvincia(provincia2);
		 * 
		 * } else { log.log(Level.INFO,
		 * "Ya existe un usuario inicializado con este identificador."); }
		 * 
		 * } catch (Exception ex) { log.log(Level.INFO,
		 * "Error al inicializar los datos en la base de datos."); }
		 */
	}
}
