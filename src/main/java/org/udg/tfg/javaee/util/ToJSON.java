package org.udg.tfg.javaee.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

import org.udg.tfg.javaee.model.Error;

import java.io.IOException;
import java.io.StringWriter;

@Singleton
public class ToJSON {

	ObjectMapper mapper;

	@PostConstruct
	void init() {
		mapper = new ObjectMapper();
	}

	public String Object(Object o) throws IOException {
		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, o);
		return sw.toString();
	}

	public String Object(Class<?> view, Object o) throws IOException {
		StringWriter sw = new StringWriter();
		mapper.writerWithView(view).writeValue(sw, o);
		return sw.toString();
	}

	public String buildError(String message) {
		try {
			return this.Object(new Error(message));
		} catch (Exception e) {
			return "{\"type\": \"Serialize error\", \"message\": \"" + e.getMessage() + "\", \"originalMsg\": "
					+ message + "\"}";
		}
	}

}
