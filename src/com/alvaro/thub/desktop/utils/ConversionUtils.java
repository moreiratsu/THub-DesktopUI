package com.alvaro.thub.desktop.utils;

public class ConversionUtils {
	
	/**
	 * Convierte un String a Long, devolviendo null si el String no es un número válido.
	 * @param s
	 * @return
	 */
	public static Long toLong(String s) {
		Long l = null;
		try {
			l = Long.parseLong(s);
		} catch (NumberFormatException e) {
			return null;
		}
		return l;
	}
}
