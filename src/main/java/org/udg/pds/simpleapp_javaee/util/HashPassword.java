package org.udg.pds.simpleapp_javaee.util;

import java.math.BigInteger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class HashPassword {

	private static final int NUMERO_ITERACIONES = 1000;

	private static final int LONGITUD_HASH = 512;

	public static String passwordHash(String password, String salt) {
		try {
			char[] chars = password.toCharArray();
			PBEKeySpec spec = new PBEKeySpec(chars, salt.getBytes(), NUMERO_ITERACIONES, LONGITUD_HASH);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return convertirAHexadecimal(hash);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean validarPassword(String originalPassword, String salt, String storedPassword) {
		return passwordHash(originalPassword, salt).equals(storedPassword);
	}

	private static String convertirAHexadecimal(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

}
