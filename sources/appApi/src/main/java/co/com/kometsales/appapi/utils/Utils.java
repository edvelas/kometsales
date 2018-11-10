package co.com.kometsales.appapi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import co.com.kometsales.appapi.exception.ServiceException;

public class Utils {

	private Utils() {

	}

	public static final String DATE_PATTERN_APP = "dd/MM/yyyy";
	public static final Pattern PATTERN_ALPHA_NUMERIC = Pattern.compile("^[A-Za-z\\d]+$");
	public static final Pattern PATTERN_NUMBER = Pattern.compile("^[\\d]+$");
	public static final Pattern PATTERN_DATE = Pattern.compile("^\\d{2}/\\d{2}/\\d{4}$");

	/**
	 * Metodo que convierte de String a Date en el formato especifico del sistema
	 * 
	 * @param strDate
	 * @return
	 * @throws ServiceException
	 */
	public static Date getStringToDate(String strDate) throws ServiceException {
		Date date = null;
		if (strDate != null && !"".equals(strDate)) {
			try {
				date = new SimpleDateFormat(DATE_PATTERN_APP).parse(strDate);
			} catch (ParseException e) {
				throw new ServiceException(e.getMessage(), ServiceException.CODE_PROBLEM_DATES);
			}
		}

		return date;
	}

	/**
	 * Metodo que valida si un email esta correcto
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	/**
	 * Metodo que valida si un String esta vacio
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isEmpty(String data) {
		boolean respuesta = true;
		if (null != data && !"".equals(data.trim())) {
			respuesta = false;
		}
		return respuesta;
	}

	/**
	 * Metodo que valida los datos ingresados
	 * 
	 * @param data
	 * @param patter
	 * @return
	 */
	public static boolean validateInputData(String data, Pattern patter) {
		boolean resultado = false;
		Matcher matcher = patter.matcher(data.toUpperCase());
		resultado = matcher.find();
		return resultado;
	}

	/**
	 * Metodo que valida el validateMaxlength
	 * 
	 * @param data
	 * @param maximoCaracteres
	 * @return
	 */
	public static boolean validateMaxlength(String data, int maximoCaracteres) {
		boolean resultado = false;
		if (data.length() <= maximoCaracteres) {
			resultado = true;
		}
		return resultado;
	}

	/**
	 * Metodo que concatena la informacion
	 * 
	 * @param separate
	 * @param data
	 * @return
	 */
	public static String concatString(String separate, String... data) {
		return Arrays.asList(data).stream().map(Object::toString).collect(Collectors.joining(separate));
	}

}
