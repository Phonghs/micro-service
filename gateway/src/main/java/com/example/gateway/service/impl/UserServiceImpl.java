package com.example.gateway.service.impl;

import com.example.common.proxy.BaseProxyService;
import com.example.common.proxy.common.enumtype.Provider;
import com.example.common.proxy.exception.BadRequestException;
import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.gateway.payload.request.AuthenticateRequest;
import com.example.gateway.security.oauth2.CustomOAuth2User;
import com.example.gateway.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl extends BaseProxyService implements UserService {

	private final PasswordEncoder passwordEncoder;


	public UserServiceImpl(PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserProfileResponse findByUserName(String username) {
		ResponseEntity<UserProfileResponse> response = getUserServiceProxy().findByUsername(buildUserServiceUri(),username);
		return Objects.isNull(response) ? null : response.getBody();
	}

	@Override
	public boolean exists(AuthenticateRequest request) {
		String userName = request.getUsername();
		UserProfileResponse user = findByUserName(userName);
		if(Objects.nonNull(user) && passwordEncoder.matches(request.getPassword(),user.getPassword())) {
			return true;
		}
		if(Objects.nonNull(user)){
			throw new BadRequestException("username or password is wrong");
		}
		return false;
	}

	@Override
	public UserProfileResponse userRegistration(UserRegistrationRequest request) {
		return getUserServiceProxy().register(buildUserServiceUri(),request).getBody();
	}

	@Override
	public UserProfileResponse processOAuth2Google(CustomOAuth2User oAuth2User) {
		UserProfileResponse user = findByUserName(oAuth2User.getEmail());
		if(Objects.isNull(user)) {
			return userRegistration(UserRegistrationRequest.builder()
					.username(oAuth2User.getEmail())
					.fullName(oAuth2User.getName())
					.email(oAuth2User.getEmail())
					.provider(Provider.GOOGLE)
					.password(passwordEncoder.encode(oAuth2User.getEmail()))
					.build());
		} else  return  user;
	}
}
