package co.com.kometsales.appapi.trucks.services;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import co.com.kometsales.appapi.exception.ServiceException;
import co.com.kometsales.appapi.trucks.exception.FileTrukException;
import co.com.kometsales.appapi.trucks.services.procesar.ITruckProcessService;

/**
 * Unique access to truck functionalities
 *
 */
@Component
public class TruckFacade {

	@Autowired
	@Qualifier("TruckProcessServiceCSV")
	private ITruckProcessService truckProcessService;
	
	/**
	 * Metodo que valida la informacion antes de iniciar el procesamiento
	 * @param emailNotify
	 * @param multipart
	 * @throws FileTrukException
	 */
	public InputStream validateInfo(String emailNotify, MultipartFile multipart) throws FileTrukException, ServiceException{
		return truckProcessService.validateInfo(emailNotify, multipart);
	}
	
	/**
	 * Metodo que realiza el procesamiento del archivo
	 * @param emailNotify
	 * @param multipart
	 */
	public void processFile(String emailNotify, InputStream inputStream){
		truckProcessService.processFile(emailNotify, inputStream);
	}
	
	

}
