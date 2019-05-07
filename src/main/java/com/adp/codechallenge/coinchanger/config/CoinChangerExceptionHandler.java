package com.adp.codechallenge.coinchanger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adp.codechallenge.coinchanger.domain.ErrorResponse;
import com.adp.codechallenge.coinchanger.exception.NotEnoughCoinsException;
import com.adp.codechallenge.coinchanger.exception.ValidationException;

@ControllerAdvice
public class CoinChangerExceptionHandler {

	Logger logger = LoggerFactory.getLogger(CoinChangerExceptionHandler.class);

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException e) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

	@ExceptionHandler({ NotEnoughCoinsException.class })
	public ResponseEntity<ErrorResponse> handleNotEnoughCoinsException(NotEnoughCoinsException e) {
		return error(HttpStatus.NOT_FOUND, e);
	}

	@ExceptionHandler({ ValidationException.class })
	public ResponseEntity<ErrorResponse> handleValidationException(ValidationException e) {
		return error(HttpStatus.BAD_REQUEST, e);
	}

	private ResponseEntity<ErrorResponse> error(HttpStatus status, Exception e) {
		ErrorResponse errResponse = new ErrorResponse();
		errResponse.setStatus(status);
		errResponse.setMessage(e.getMessage());
		logger.error(e.getMessage(),e);
		return ResponseEntity.status(status).body(errResponse);
	}

}
