package org.udg.tfg.javaee.job;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.udg.tfg.javaee.model.Evento;
import org.udg.tfg.javaee.service.EventoService;

/**
 * Clase encargada de ejecutar un proceso destinado a actualizar el estado de
 * los eventos deportivos de la aplicación.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
@Stateless
@LocalBean
public class ActualizarEstadoEvento {

	/**
	 * Bean encargado de ejecutar la lógica de negocio de las acciones sobre los
	 * eventos deportivos.
	 */
	@EJB
	EventoService eventoService;

	/**
	 * Encargado de logear en consola.
	 */
	@Inject
	private Logger log;

	/**
	 * Método encargado de ejecutar un proceso asíncrono cada minuto. Cambia el
	 * estado a finalizado de los eventos deportivos si ya se han celebrado respecto
	 * a la fecha actual.
	 * 
	 */
	@Schedule(second = "*/60", minute = "*", hour = "*", persistent = false)
	public void doWork() {
		List<Evento> eventos = eventoService.obtenerEventosNoFinalizados();
		log.log(Level.INFO, "Eventos recuperados: " + eventos.size());
		for (Evento evento : eventos) {
			eventoService.finalizarEvento(evento);
			log.log(Level.INFO, "El evento: " + evento.getTitulo() + " ha sido finalizado.");
		}

	}

}
