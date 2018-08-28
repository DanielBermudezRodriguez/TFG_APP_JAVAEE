package org.udg.tfg.javaee.util;

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

	}
}
