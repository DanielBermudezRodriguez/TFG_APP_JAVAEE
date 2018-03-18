package response;

import org.udg.pds.simpleapp_javaee.model.Usuario;

public class ResponseUsuario {

	public static class ResponseLoginUsuario {
		public Long id;

		public ResponseLoginUsuario(Usuario u) {
			this.id = u.getId();
		}
	}

	public static class ResponseRegistroUsuario {
		public Long id;

		public ResponseRegistroUsuario(Usuario u) {
			this.id = u.getId();
		}
	}

}
