package org.udg.tfg.javaee.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Esta clase se encarga de definir el método para serializar fechas a formato
 * JSON.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

	/**
	 * Formato de fecha al cual se desea serializar las fechas a JSON.
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	/**
	 * Método encargado de serializar una fecha a formato JSON.
	 * 
	 * @param date
	 *            fecha a serializar.
	 * @param gen
	 *            generador de JSON.
	 * @param provider
	 *            Provedor de serialización.
	 * @throws JsonProcessingException
	 *             error serializando la fecha
	 */
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String formattedDate = dateFormat.format(date);
		gen.writeString(formattedDate);
	}

}
