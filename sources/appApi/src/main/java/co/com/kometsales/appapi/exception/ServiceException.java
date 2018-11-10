/**
 * 
 */
package co.com.kometsales.appapi.exception;

/**
 * @author edwaveor Clase generica para el manejo de exepciones de los servicios
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final long CODE_EXCEPTION_GENERAL = 1;
	public static final long CODE_NO_ENCUENTRA_INFO = 2;
	public static final long CODE_PROBLEM_DATES = 3;
	
	public static final long CODE_EXCEPTION_PROCESS_FILE = 2;
	public static final String MSG_EXCEPTION_PROCESS_FILEL = "fileTrukException.msg.process.file";

	private final long codigo;

	public ServiceException(String mensaje, long codigo) {
		super(mensaje);
		this.codigo = codigo;
	}

	public ServiceException(String mensaje, long codigo, Throwable throwable) {
		super(mensaje, throwable);
		this.codigo = codigo;
	}

	public long getCodigo() {
		return codigo;
	}

	public String getTracerAll() {
		StringBuilder strException = new StringBuilder();
		strException.append("Mensaje: " + getMessage());
		strException.append("Causa: " + getCause());
		return strException.toString();
	}
}
