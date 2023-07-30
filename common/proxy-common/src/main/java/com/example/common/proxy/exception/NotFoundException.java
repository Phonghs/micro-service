package com.example.common.proxy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseApiException {
	private static final long serialVersionUID = 7375355036070030240L;

	public NotFoundException() {
	}

	public NotFoundException(String msg) {
		super(msg);
		super.code(HttpStatus.NOT_FOUND.value());
	}
}
