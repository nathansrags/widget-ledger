package com.widget.ledger.web.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author 175049
 *
 */
@ControllerAdvice
public class UnCheckedExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ RecordNotFoundException.class })
	protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
		RecordNotFoundException ire = (RecordNotFoundException) e;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, ire.getMessage(), headers, HttpStatus.OK, request);
	}

	@ExceptionHandler({ BusinessUncheckedException.class })
	protected ResponseEntity<Object> handleBusinessUncheckedException(RuntimeException e, WebRequest request) {
		BusinessUncheckedException ire = (BusinessUncheckedException) e;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, ire.getMessage(), headers, HttpStatus.OK, request);
	}
	
	@ExceptionHandler({ NullPointerException.class })
	protected ResponseEntity<Object> handleNullPointer(RuntimeException e, WebRequest request) {
		BusinessUncheckedException ire = (BusinessUncheckedException) e;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(e, ire.getMessage(), headers, HttpStatus.OK, request);
	}

}
