package org.udg.tfg.javaee.rest.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JsonDateSerializer extends JsonSerializer<Date> {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

  @Override
  public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
      throws IOException, JsonProcessingException {
    String formattedDate = dateFormat.format(date);
    gen.writeString(formattedDate);
  }
  
}
