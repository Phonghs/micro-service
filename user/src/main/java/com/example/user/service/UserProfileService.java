package com.example.user.service;


import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;

import java.util.List;

public interface UserProfileService {
	UserProfileResponse findByUsername(String username);

	UserProfileResponse register(UserRegistrationRequest request);

	UserProfileResponse changePassword(String username,String oldPassword, String newPassword);

	List<UserProfileResponse> getListStudent();
}
