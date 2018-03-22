package response;

import java.util.Date;
import java.util.List;
import org.udg.pds.simpleapp_javaee.model.Deporte;
import org.udg.pds.simpleapp_javaee.model.Municipio;
import org.udg.pds.simpleapp_javaee.model.Pais;
import org.udg.pds.simpleapp_javaee.model.Provincia;
import org.udg.pds.simpleapp_javaee.model.Usuario;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateDeserializer;
import org.udg.pds.simpleapp_javaee.rest.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ResponseUsuario {

	public static class ResponseInformacionUsuario {

		public String username;
		public String nombre;
		public String apellidos;
		public String telefono;
		public String email;
		@JsonSerialize(using = JsonDateSerializer.class)
		@JsonDeserialize(as = JsonDateDeserializer.class)
		public Date fechaRegistro;
		public Municipio municipio;
		public Provincia provincia;
		public Pais pais;
		public List<Deporte> deportesFavoritos;

		public ResponseInformacionUsuario(Usuario u) {
			this.username = u.getUsername();
			this.nombre = u.getNombre();
			this.apellidos = u.getApellidos();
			this.telefono = u.getTelefono();
			this.email = u.getEmail();
			this.fechaRegistro = u.getFechaRegistro();
			this.municipio = u.getMunicipio();
			this.provincia = u.getMunicipio().getProvincia();
			this.pais = u.getMunicipio().getProvincia().getPais();
			this.deportesFavoritos = u.getDeportesFavoritos();
		}

	}

}
