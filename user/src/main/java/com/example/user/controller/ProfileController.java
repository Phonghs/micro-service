package com.example.user.controller;

import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.user.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final UserProfileService userProfileService;

	public ProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@GetMapping("/find-by-username")
	ResponseEntity<UserProfileResponse> findByUserName(
			@RequestParam String username
	){
		UserProfileResponse userProfileResponse = userProfileService.findByUsername(username);
		return ResponseEntity.ok(userProfileResponse);
	}

	@PostMapping("/register")
	ResponseEntity<UserProfileResponse> register(@RequestBody UserRegistrationRequest request) {
		return ResponseEntity.ok(userProfileService.register(request));
	}

	@PostMapping("change-password")
	ResponseEntity<UserProfileResponse> changePassword(
			@RequestParam String username,
			@RequestParam(value = "old_password") String oldPassword,
			@RequestParam(value = "new_password") String newPassword
	) {
		return ResponseEntity.ok(userProfileService.changePassword(username,oldPassword,newPassword));
	}

}
