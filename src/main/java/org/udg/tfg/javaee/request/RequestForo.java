package org.udg.tfg.javaee.request;

import javax.validation.constraints.NotNull;

public class RequestForo {

	public static class RequestMensajeForo{
		@NotNull
		public Long idForo;
		@NotNull
		public String mensaje;
	}
	
}
