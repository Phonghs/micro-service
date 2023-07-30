package com.example.gateway.service;

import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.gateway.payload.request.AuthenticateRequest;
import com.example.gateway.security.oauth2.CustomOAuth2User;

public interface UserService {
	UserProfileResponse findByUserName(String username);

	boolean exists(AuthenticateRequest request);

	UserProfileResponse userRegistration(UserRegistrationRequest request);

	UserProfileResponse processOAuth2Google(CustomOAuth2User user);
}
