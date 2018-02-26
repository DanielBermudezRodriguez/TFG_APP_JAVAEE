package org.udg.pds.simpleapp_javaee.util;

import org.udg.pds.simpleapp_javaee.model.Task;
import org.udg.pds.simpleapp_javaee.model.Usuario;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
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

    try {
      Usuario exists = em.find(Usuario.class, 1L);
      if (exists == null) {
        Usuario u = new Usuario("jo", "jo@hotmail.com", "jo","Daniel","Bermudez Rodriguez","646094314");
        em.persist(u);
        Task t1 = new Task(new Date(), new Date(), false, "Tarea número 1");
        u.addTask(t1);
        t1.setUser(u);

        Usuario u2 = new Usuario("tu", "tu@hotmail.com", "tu","Sergio","Bermudez Rodriguez","629725139");
        em.persist(u2);
        Task t2 = new Task(new Date(), new Date(), false, "Tarea número 2");
        u2.addTask(t2);
        t2.setUser(u2);

      } else {
        log.log(Level.INFO, "Ya existe un usuario inicializado con este identificador.");
      }

    } catch (Exception ex) {
      log.log(Level.INFO, "Error al inicializar los datos en la base de datos.");
    }
  }
}
