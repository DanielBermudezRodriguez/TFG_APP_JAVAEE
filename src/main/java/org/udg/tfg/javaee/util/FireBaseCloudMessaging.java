package org.udg.tfg.javaee.util;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FireBaseCloudMessaging {

	private FirebaseApp fireBaseApp;

	private static FireBaseCloudMessaging fireBase = null;

	@Inject
	private Logger log;

	public FireBaseCloudMessaging() {
		try {

			FileInputStream serviceAccount = new FileInputStream(
					"C:\\Users\\Dani\\TFG\\ServerJEE\\src\\main\\resources\\serviceAccountKey.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl(Global.DATABASE_URL_FIREBASE).build();

			fireBaseApp = FirebaseApp.initializeApp(options);

		} catch (Exception e) {
			log.log(Level.SEVERE, "Error al intentar abrir el fichero: " + e.getMessage());
		}

	}

	public static FireBaseCloudMessaging getInstance() {
		if (fireBase == null) {
			fireBase = new FireBaseCloudMessaging();
		}
		return fireBase;
	}

	public FirebaseApp getFireBaseApp() {
		return fireBaseApp;
	}

}
