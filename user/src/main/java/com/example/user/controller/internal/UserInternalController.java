package com.example.user.controller.internal;

import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.user.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal-user")
public class UserInternalController {

	private final UserProfileService userProfileService;

	public UserInternalController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@GetMapping("/list-student")
	ResponseEntity<List<UserProfileResponse>> getListStudent() {
		return ResponseEntity.ok(userProfileService.getListStudent());
	}
}
