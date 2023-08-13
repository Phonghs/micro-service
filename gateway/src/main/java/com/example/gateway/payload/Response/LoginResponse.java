package com.example.gateway.payload.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

	@JsonProperty("access_token")
	private String accessToken;

	private String userName;

	private String fullName;

	private String avatar;

	private String email;

	private List<String> role;

	private String authorId;
}
