package com.example.common.proxy.user.proxy;

import com.example.common.proxy.config.FeignConfig;
import com.example.common.proxy.user.payload.request.UserRegistrationRequest;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@FeignClient(name = "phong-user-servive", url = "NOT_USED" // template
,configuration = FeignConfig.class)
public interface UserServiceProxy {

	@GetMapping(value = "/profile/find-by-username",
			headers = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserProfileResponse> findByUsername(
			URI uri,
			@RequestParam String username
	);

	@PostMapping("/profile/register")
	ResponseEntity<UserProfileResponse> register(
			URI uri,
			@RequestBody UserRegistrationRequest request
	);

	@GetMapping("/internal-user/list-student")
	ResponseEntity<List<UserProfileResponse>> getListStudent(
			URI uri
	) ;
}
