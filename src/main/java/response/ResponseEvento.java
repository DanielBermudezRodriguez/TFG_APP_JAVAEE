package response;

import java.util.Date;

import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Estado;
import org.udg.pds.simpleapp_javaee.model.Evento;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ResponseEvento {

	public static class ResponseEventoInformacion {

		public Long id;
		public String titulo;
		public String descripcion;
		public int duracion;
		public int numeroParticipantes;
		@JsonSerialize(using = JsonDateSerializer.class)
		@JsonDeserialize(as = JsonDateDeserializer.class)
		public Date fechaEvento;
		public Deporte deporte;
		public Estado estado;
		public int participantesRegistrados;
		public ResponseAdministradorEvento administrador;
		public Municipio municipio;

		public ResponseEventoInformacion(Evento e, Integer participantesRegistrados) {
			this.id = e.getId();
			this.titulo = e.getTitulo();
			this.descripcion = e.getDescripcion();
			this.duracion = e.getDuracion();
			this.numeroParticipantes = e.getNumeroParticipantes();
			this.fechaEvento = e.getFechaEvento();
			this.deporte = e.getDeporte();
			this.estado = e.getEstado();
			this.participantesRegistrados = participantesRegistrados;
			this.administrador = new ResponseAdministradorEvento(e.getAdministrador());
			if (e.getUbicacionGPS() != null)
				this.municipio = e.getUbicacionGPS().getMunicipio();
			else if (e.getMunicipio() != null)
				this.municipio = e.getMunicipio();

		}

	}

	public static class ResponseAdministradorEvento {
		public Long id;
		public String username;

		public ResponseAdministradorEvento(Usuario u) {
			this.id = u.getId();
			this.username = u.getUsername();
		}

	}

}
