package org.udg.pds.simpleapp_javaee.util.notificaciones;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.udg.pds.simpleapp_javaee.util.Global;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@LocalBean
public class EnviarNotificacion {

	public static void enviarNotificacion(Notificacion notificacion) {

		try {

			// Abrir conexión URL de google FireBase
			URL url = new URL(Global.URL_FIREBASE_GOOGLE);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// Configurar parámetros petición
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "key=" + Global.API_KEY_FIREBASE);
			conn.setDoOutput(true);

			// Añadir contenido de la notificación en un JSON a la petición
			ObjectMapper mapper = new ObjectMapper();
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			mapper.writeValue(wr, notificacion);
			wr.flush();
			wr.close();

			// Procesar respuesta
			int responseCode = conn.getResponseCode();
			System.out.println("Enviando petición POST a la URL: " + url.toString());
			System.out.println("Código de respuesta: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println("Contenido respuesta: " + response.toString());

		} catch (Exception e) {
			System.out.println("ERROR al enviar la notificación");
		}

	}

}
