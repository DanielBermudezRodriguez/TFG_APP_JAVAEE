package response;

import org.udg.pds.simpleapp_javaee.model.Municipio;

public class ResponseMunicipio {

	public static class ResponseInformacionMunicipio {

		public Long id;
		public String municipio;

		public ResponseInformacionMunicipio(Municipio m) {
			this.id = m.getId();
			this.municipio = m.getMunicipio();
		}

	}

}
