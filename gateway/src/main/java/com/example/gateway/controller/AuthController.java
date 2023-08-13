package com.example.gateway.controller;

import com.example.common.proxy.exception.BadRequestException;
import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import com.example.gateway.payload.Response.LoginResponse;
import com.example.gateway.payload.request.AuthenticateRequest;
import com.example.gateway.service.UserService;
import com.example.gateway.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController{

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;


	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody @Valid AuthenticateRequest request) {
		if(userService.exists(request)) {
			UserProfileResponse user = userService.findByUserName(request.getUsername());
			return ResponseEntity.ok(LoginResponse.builder()
					.accessToken(jwtUtils.generateToken(request.getUsername()))
					.avatar(user.getAvatar())
					.userName(user.getUsername())
					.fullName(user.getName())
					.role(user.getRole())
					.email(user.getEmail())
					.build());
		}
		throw new BadRequestException("user name are not exits");
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserRegistrationRequest request) {
		userService.userRegistration(request);
		return ResponseEntity.ok("SUCCESS");
	}

	@GetMapping
	public String test(){
		return "Ok";
	}
}
