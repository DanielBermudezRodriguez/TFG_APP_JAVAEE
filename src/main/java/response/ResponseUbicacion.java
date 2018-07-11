package response;

public class ResponseUbicacion {

	public static class ResponseUbicacionEvento {

		public Double latitud;
		public Double longitud;
		public String direccion;
		public String municipio;

		public ResponseUbicacionEvento(Double latitud, Double longitud, String direccion, String municipio) {
			this.latitud = latitud;
			this.longitud = longitud;
			this.direccion = direccion;
			this.municipio = municipio;
		}

	}

}
