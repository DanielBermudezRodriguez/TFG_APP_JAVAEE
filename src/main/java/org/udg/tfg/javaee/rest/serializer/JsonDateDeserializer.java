package org.udg.tfg.javaee.rest.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Esta clase se encarga de definir el método para deserializar fechas obtenidas
 * mediante el formato JSON.
 * 
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

	/**
	 * Formato de fecha al cual se desea deserializar las fechas obtenidas en JSON.
	 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	/**
	 * Método encargado de deserializar una fecha incluida en formato JSON.
	 * 
	 * @param jsonParser
	 *            contenido del JSON a deserializar.
	 * @return Devuelve la fecha deserializada en el formato deseado o null si se ha
	 *         producido algún error.
	 * @exception e
	 *                en caso de que se produzca algún error en el proceso de
	 *                deserialización de la fecha.
	 * @throws JsonProcessingException
	 *             error deserializando la fecha
	 */
	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JsonProcessingException {
		try {
			return dateFormat.parse(jsonParser.getValueAsString());
		} catch (ParseException e) {
			return null;
		}
	}

}
