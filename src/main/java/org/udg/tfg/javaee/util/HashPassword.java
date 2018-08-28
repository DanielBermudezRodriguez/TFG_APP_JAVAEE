package org.udg.tfg.javaee.util;

import java.math.BigInteger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Esta clase se encarga de cifrar y validar las contraseñas de los usuarios de la aplicación.
 * @author: Daniel Bermudez Rodriguez
 * @version: 1.0
 */
public final class HashPassword {

	/**
	 * Número de iteraciones a realizar a la hora de cifrar una contraseña.
	 */
	private static final int NUMERO_ITERACIONES = 1000;

	/**
	 * Tamaño en Bytes de la contraseña cifrada.
	 */
	private static final int LONGITUD_HASH = 512;

	/**
	* Método encargado de cifrar una contraseña.
	* @param password valor de la contraseña a cifrar.
	* @param salt valor de la salt a utilizar en el proceso de cifrado.
	* @return Devuelve la contraseña cifrada o null si se ha producido algun error durante el proceso de cifrado.
	* @exception e en caso de que se produzca algun error en el proceso de cifrado.
	*/
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

	/**
	* Método encargado de validar una contraseña de un usuario en el momento de iniciar sesión.
	* @param originalPassword valor de la contraseña introducida al iniciar sesión.
	* @param salt valor de la salt a utilizar en el proceso de cifrado.
	* @param storedPassword valor de la contraseña cifrada en base de datos.
	* @return Devuelve true si al cifrar la contraseña originalPassword coincide con el valor de storedPassword.
	*/
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
