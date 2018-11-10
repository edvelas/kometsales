/**
 * 
 */
package co.com.kometsales.appapi.config.response;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.kometsales.appapi.exception.ServiceException;
import co.com.kometsales.appapi.trucks.exception.FileTrukException;
import co.com.kometsales.appapi.utils.Messages;

/**
 * Clase que detecta un error en el sistema y lo encapsula.
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Messages messages;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, messages.getMessages("restExceptionHandler.handleHttpMessageNotReadable"), ex));
	}

	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, messages.getMessages("restExceptionHandler.handleAccessDeniedException"), ex));
	}

	@ExceptionHandler({ ServiceException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> serviceException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex));
	}
	
	@ExceptionHandler({ FileTrukException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> fileTrukException(Exception ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
