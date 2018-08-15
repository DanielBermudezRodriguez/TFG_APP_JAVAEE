package org.udg.tfg.javaee.response;

import org.udg.tfg.javaee.model.Municipio;

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
