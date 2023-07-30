package com.example.common.proxy.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;

@Setter
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true, chain = true)
public class BaseApiException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 5186826748767860928L;

	@Getter
	private int code = 500;

	@Getter
	private String displayMessage;

	@Getter
	private Object extraData = Collections.EMPTY_MAP;

	public BaseApiException() {
		super();
	}

	public BaseApiException(String message) {
		super(message);
	}
}
