package org.udg.tfg.javaee.request;

import javax.validation.constraints.NotNull;

public class RequestUbicacion {
	
	public static class RequestUbicacionUsuario {
		@NotNull
		public double latitud;
		@NotNull
		public double longitud;
		@NotNull
		public String direccion;
		@NotNull
		public String municipio;
		
	}

}
