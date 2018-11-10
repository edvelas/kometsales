package co.com.kometsales.appapi.trucks.services.procesar.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.com.kometsales.appapi.exception.ServiceException;
import co.com.kometsales.appapi.trucks.dto.TruckDTO;
import co.com.kometsales.appapi.trucks.dto.TypeValidation;
import co.com.kometsales.appapi.trucks.entity.Truck;
import co.com.kometsales.appapi.trucks.exception.FileTrukException;
import co.com.kometsales.appapi.trucks.services.ITruckRepository;
import co.com.kometsales.appapi.trucks.services.procesar.ITruckProcessService;
import co.com.kometsales.appapi.utils.Messages;
import co.com.kometsales.appapi.utils.Utils;
import co.com.kometsales.appapi.utils.csvutils.CsvUtils;
import co.com.kometsales.appapi.utils.sendemail.SendEmail;
import co.com.kometsales.appapi.utils.sendemail.dto.Mail;

@Service("TruckProcessServiceCSV")
public class TruckProcessServiceCSV implements ITruckProcessService {

	public static final String PERMIT_EXTENSION = "CSV";

	Logger logger = LoggerFactory.getLogger(TruckProcessServiceCSV.class);

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	private Messages messages;

	@Autowired
	private ITruckRepository truckRepository;

	/**
	 * Metodo que valida la informacion antes de procesarla
	 * 
	 * @param emailNotify
	 * @param multipart
	 * @throws FileTrukException
	 * @throws ServiceException 
	 */
	@Override
	public InputStream validateInfo(String emailNotify, MultipartFile multipart) throws FileTrukException, ServiceException {

		if (multipart == null || multipart.isEmpty()) {
			throw new FileTrukException(messages.getMessages(FileTrukException.MSG_EXCEPTION_FILE_EMTY),
					FileTrukException.CODE_EXCEPTION_INFO_EMTY);
		}

		if (Utils.isEmpty(emailNotify)) {
			throw new FileTrukException(messages.getMessages(FileTrukException.MSG_EXCEPTION_EMAIL_EMTY),
					FileTrukException.CODE_EXCEPTION_INFO_EMTY);
		}

		String ext = FilenameUtils.getExtension(multipart.getOriginalFilename()).toUpperCase();
		if (!PERMIT_EXTENSION.equals(ext)) {
			throw new FileTrukException(messages.getMessages(FileTrukException.MSG_EXCEPTION_EXT),
					FileTrukException.CODE_EXCEPTION_EXT);
		}

		if (!Utils.isValidEmailAddress(emailNotify)) {
			throw new FileTrukException(messages.getMessages(FileTrukException.MSG_EXCEPTION_EMAIL),
					FileTrukException.CODE_EXCEPTION_EMAIL);
		}
		
		try {
			return multipart.getInputStream();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(messages.getMessages(ServiceException.MSG_EXCEPTION_PROCESS_FILEL), ServiceException.CODE_EXCEPTION_PROCESS_FILE);
		}

	}

	/**
	 * Metodo de procesamiento de los archivos
	 * 
	 * @param emailNotificacion
	 * @param multipart
	 * @throws ExcelException
	 */
	@Override
	@Async
	public void processFile(String emailNotify, InputStream inputStream) {
		try {
			List<TruckDTO> dataFile = CsvUtils.read(TruckDTO.class, inputStream);

			List<Truck> listCreate = validateTruckList(dataFile);

			saveTrucks(listCreate);
			
			sendReportMessage(emailNotify, dataFile);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			sendMessageError(emailNotify, e.getMessage(), ExceptionUtils.getStackTrace(e).substring(4000));
		}

	}

	/**
	 * Metodo que guarda la informacion de los camiones
	 * 
	 * @param listCreate
	 */
	@Transactional
	private void saveTrucks(List<Truck> listCreate) {
		if(listCreate != null && !listCreate.isEmpty()) {
			truckRepository.saveAll(listCreate);
		}
	}

	/**
	 * Metodo que realiza las validaciones de los camiones a procesar
	 * 
	 * @param dataFile
	 * @return
	 * @throws ServiceException
	 */
	private List<Truck> validateTruckList(List<TruckDTO> dataFile) throws ServiceException {
		List<Truck> listOk = new ArrayList<>();
		for (TruckDTO truckDTO : dataFile) {
			validateTruckDTO(truckDTO);

			if (truckDTO.isValid()) {
				listOk.add(new Truck(truckDTO));
			}
		}

		return listOk;
	}

	/**
	 * Metodo que valida la informacion de un camion
	 * 
	 * @param truckDTO
	 */
	private void validateTruckDTO(TruckDTO truckDTO) {
		List<String> inconsistencies = new ArrayList<>();
		isValidInput(truckDTO.getCarPlate(), TypeValidation.CARPLATE, inconsistencies);
		isValidInput(truckDTO.getColor(), TypeValidation.COLOR, inconsistencies);
		isValidInput(truckDTO.getDateLoad(), TypeValidation.DATELOAD, inconsistencies);
		isValidInput(truckDTO.getModel(), TypeValidation.MODEL, inconsistencies);
		isValidInput(truckDTO.getRegistration(), TypeValidation.REGISTRATION, inconsistencies);

		if (!inconsistencies.isEmpty()) {
			truckDTO.setValid(false);
			truckDTO.setInconsistencies(inconsistencies);
		}

	}
	
	/**
	 * Metodo que valida los datos de entrada
	 * @param data
	 * @param typeValidation
	 * @param inconsistencies
	 */
	private void isValidInput(String data, TypeValidation typeValidation, List<String> inconsistencies){		
		boolean isEmpty = Utils.isEmpty(data);		
		if (typeValidation.isRequired() && isEmpty){
			inconsistencies.add(messages.getMessages("utils.isValidInput.isRequired", new String[] { typeValidation.getName(), data, typeValidation.getExample() }));
		}		
		if(!isEmpty){
			if(!Utils.validateMaxlength(data, typeValidation.getMaxlength())){
				inconsistencies.add(messages.getMessages("utils.isValidInput.validateMaxlength", new String[] { typeValidation.getName(), String.valueOf(data.length()), String.valueOf(typeValidation.getMaxlength()) }));
						
			} else if (!Utils.validateInputData(data, typeValidation.getRegex())){
				inconsistencies.add(messages.getMessages("utils.isValidInput.validateInputData", new String[] { typeValidation.getName(), data, typeValidation.getExample() }));
			}
		}	
	}

	/**
	 * Metodo que genera el informe del prceso
	 * @param emailNotify
	 * @param dataFile
	 */
	private void sendReportMessage(String emailNotify, List<TruckDTO> dataFile) {
		StringBuilder stringBuilder = new StringBuilder();
		int proccesLine = 0;
		int proccesLineErro = 0;
		StringBuilder stringBuilderDetail = new StringBuilder();
		
		for (TruckDTO truckDTO : dataFile) {
			if(truckDTO.isValid()) {
				proccesLine++;
			}else {
				proccesLineErro++;
				stringBuilderDetail.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.detailError.line", new String[] { truckDTO.getLine() }));
				stringBuilderDetail.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.detailError.errores"));
				stringBuilderDetail.append(truckDTO.getInconsistencies().stream().map(Object::toString).collect(Collectors.joining("\n")));
			}
		}
		
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.main"));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.total", new String[] { String.valueOf(dataFile.size()) }));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.success", new String[] { String.valueOf(proccesLine) }));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.error", new String[] { String.valueOf(proccesLineErro) }));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendReportMessage.detailError"));
		stringBuilder.append(stringBuilderDetail);
		sendEmail(messages.getMessages("truckProcessServiceCSV.sendReportMessage.subject"), stringBuilder.toString(), emailNotify);
	}
	

	/**
	 * Metodo que genera el mensaje cuando se presenta un error en el procesamiento
	 * @param emailNotifya 
	 * @param message
	 * @param error
	 */
	private void sendMessageError(String emailNotify, String message, String error) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendMessageError.main"));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendMessageError.title"));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendMessageError.menssage", new String[] { message }));
		stringBuilder.append(messages.getMessages("truckProcessServiceCSV.sendMessageError.error", new String[] { error }));
		
		sendEmail(messages.getMessages("truckProcessServiceCSV.sendReportMessage.subject"), message, emailNotify);
	}

	/**
	 * Metodo que se encarga de enviar el email
	 * 
	 * @param mail
	 */
	private void sendEmail(String subject, String content, String emailNotify) {
		try {
			Mail mail = new Mail("edvelas@gmail.com", emailNotify, subject, content);

			sendEmail.sendEmailApp(mail);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
