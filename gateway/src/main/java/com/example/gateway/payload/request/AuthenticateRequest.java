package com.example.gateway.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AuthenticateRequest {

	@NotNull
	private String username;

	@NotNull
	private String password;
}
