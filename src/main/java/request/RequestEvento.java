package request;

import javax.validation.constraints.NotNull;
import request.RequestUbicacion.RequestUbicacionUsuario;

public class RequestEvento {

	public static class RequestCrearEvento {
		@NotNull
		public String titulo;
		@NotNull
		public String descripcion;
		@NotNull
		public int duracion;
		@NotNull
		public int numeroParticipantes;
		@NotNull
		public String fechaEvento;
		@NotNull
		public Boolean esPublico;
		public Long municipio;
		@NotNull
		public Long deporte;
		public RequestUbicacionUsuario ubicacionGPS;
		@NotNull
		public String tituloForo;
	}

}
