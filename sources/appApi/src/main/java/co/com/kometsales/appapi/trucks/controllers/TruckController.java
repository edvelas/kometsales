package co.com.kometsales.appapi.trucks.controllers;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.kometsales.appapi.exception.ServiceException;
import co.com.kometsales.appapi.trucks.exception.FileTrukException;
import co.com.kometsales.appapi.trucks.services.TruckFacade;
import co.com.kometsales.appapi.utils.Messages;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/trucks/v1/")
@Api("Endpoints for loading truck information.")
public class TruckController {

	@Autowired
	private TruckFacade truckFacade;
	@Autowired
	Messages mensajes;

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/upload", produces = "application/json")
	@ApiOperation(value = "REST for process information from a CSV file and store it in the system [CARPLATE: DEV100, MODEL: 2012, REGISTRATION: ENVIGADO, COLOR: BLANCO, DATELOAD: 01/10/2012].", response = String.class)

	public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile multipart,
			@RequestHeader(value = "email") String emailNotify) throws FileTrukException, ServiceException {

		InputStream inputStream = truckFacade.validateInfo(emailNotify, multipart);

		truckFacade.processFile(emailNotify, inputStream);
		
		String message = mensajes.getMessages("truckController.processFile.ok", new String[] { emailNotify });
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(message);
	}

}
