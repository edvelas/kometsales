package co.com.kometsales.appapi.trucks.services.procesar;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import co.com.kometsales.appapi.exception.ServiceException;
import co.com.kometsales.appapi.trucks.exception.FileTrukException;

/**
 * Interface for truck processing
 * 
 * @author ED
 *
 */
public interface ITruckProcessService {

	/**
	 * Metodo que valida la informacion antes de procesarla
	 * @param emailNotify
	 * @param multipart
	 * @throws FileTrukException
	 */
	public InputStream validateInfo(String emailNotify, MultipartFile multipart) throws FileTrukException, ServiceException;
	
	/**
	 * Metodo de procesamiento de los archivos
	 * 
	 * @param emailNotificacion
	 * @param multipart
	 * @throws ExcelException
	 */
	public void processFile(String emailNotify, InputStream inputStream);
}
