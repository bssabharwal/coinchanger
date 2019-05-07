package com.adp.codechallenge.coinchanger.domain;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private HttpStatus status;
	private String message;

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
