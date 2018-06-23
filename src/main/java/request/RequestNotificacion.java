package request;

import javax.validation.constraints.NotNull;

public class RequestNotificacion {

	public static class RequestModificarNotificacion {

		@NotNull
		public Boolean altaUsuario;

		@NotNull
		public Boolean bajaUsuario;

		@NotNull
		public Boolean eventoCancelado;

		@NotNull
		public Boolean datosModificados;

		@NotNull
		public Boolean usuarioEliminado;
	}

}
