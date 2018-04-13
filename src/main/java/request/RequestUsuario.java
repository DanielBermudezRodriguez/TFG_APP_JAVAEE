package request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RequestUsuario {

	public static class RequestLoginUsuario {
		@NotNull
		public String email;
		@NotNull
		public String password;
		@NotNull
		public String tokenFireBase;
	}

	public static class RequestRegistroUsuario {
		@NotNull
		public String username;
		@NotNull
		public String email;
		@NotNull
		public String password;
		@NotNull
		public String nombre;
		@NotNull
		public String apellidos;
		@NotNull
		public String tokenFireBase;
		@NotNull
		public Long municipio;
		@NotNull
		public List<Long> deportesFavoritos;

	}

	public static class RequestModificarUsuario {
		@NotNull
		public String username;
		@NotNull
		public String nombre;
		@NotNull
		public String apellidos;
		@NotNull
		public Long municipio;
		@NotNull
		public List<Long> deportesFavoritos;

	}

}
