package com.example.common.proxy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseApiException{
	private static final long serialVersionUID = 7375355036070030240L;

	public BadRequestException() {
	}

	public BadRequestException(String msg) {
		super(msg);
		super.code(HttpStatus.BAD_REQUEST.value());
	}

	public BadRequestException(String msg, String displayMsg) {
		super(msg);
		super.displayMessage(displayMsg);
		super.code(HttpStatus.BAD_REQUEST.value());
	}
}
