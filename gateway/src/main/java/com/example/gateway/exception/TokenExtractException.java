package com.example.gateway.exception;

public class TokenExtractException extends JWTVerificationException {

	private static final long serialVersionUID = 5080815165304725967L;

	public TokenExtractException(String message) {
		super(message);
	}
}
