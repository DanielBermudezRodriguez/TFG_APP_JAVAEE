package org.udg.pds.simpleapp_javaee.util;

import java.io.FileInputStream;
import java.io.IOException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FireBaseCloudMessaging {

	private FirebaseApp fireBaseApp;

	private static FireBaseCloudMessaging fireBase = null;

	public FireBaseCloudMessaging() throws IOException {

		FileInputStream serviceAccount = new FileInputStream(
				"\\TODOjavaee\\src\\main\\java\\org\\udg\\pds\\simpleapp_javaee\\util\\serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://tfg-geinf.firebaseio.com").build();

		fireBaseApp = FirebaseApp.initializeApp(options);

	}

	public static FireBaseCloudMessaging getInstance() throws IOException {
		if (fireBase == null) {
			fireBase = new FireBaseCloudMessaging();
		}
		return fireBase;
	}

	public FirebaseApp getFireBaseApp() {
		return fireBaseApp;
	}

}
