package job;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.service.EventoService;

@Stateless
@LocalBean
public class ActualizarEstadoEvento {

	@EJB
	EventoService eventoService;

	@Inject
	private Logger log;

	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	public void doWork() {
		List<Evento> eventos = eventoService.obtenerEventosNoFinalizados();
		log.log(Level.INFO, "Eventos recuperados: " + eventos.size());
		for (Evento evento : eventos) {
			eventoService.finalizarEvento(evento);
			log.log(Level.INFO, "El evento: " + evento.getTitulo() + " ha sido finalizado.");
		}

	}

}
