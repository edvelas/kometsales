package co.com.kometsales.appapi.trucks.exception;

public class FileTrukException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final long codigo;
	
	public static final long CODE_EXCEPTION_INFO_EMTY = 1;
	public static final String MSG_EXCEPTION_FILE_EMTY = "fileTrukException.msg.fileEmpty";
	public static final String MSG_EXCEPTION_EMAIL_EMTY = "fileTrukException.msg.emailEmpty";
	
	public static final long CODE_EXCEPTION_EXT = 3;
	public static final String MSG_EXCEPTION_EXT = "fileTrukException.msg.ext";
	
	public static final long CODE_EXCEPTION_EMAIL = 4;
	public static final String MSG_EXCEPTION_EMAIL = "fileTrukException.msg.email";
	
	

	public FileTrukException(String mensaje, long codigo) {
		super(mensaje);
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
