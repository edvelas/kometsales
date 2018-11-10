package co.com.kometsales.appapi.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Clase para manejar la captura de los mensajes
 *
 */
@Service
public class Messages {

	@Autowired
	private MessageSource messageSource;

	/**
	 * Obtiene un mensaje por key
	 * @param key
	 * @return
	 */
	public String getMessages(String key) {
		return getMessages(key, null);
	}

	/**
	 * Optiene un mensaje con key y le envia parametros al mensaje
	 * @param key
	 * @param params
	 * @return
	 */
	public String getMessages(String key, String[] params) {
		return messageSource.getMessage(key, params, Locale.getDefault());
	}

}
