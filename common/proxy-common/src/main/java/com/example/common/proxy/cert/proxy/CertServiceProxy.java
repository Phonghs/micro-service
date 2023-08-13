package com.example.common.proxy.cert.proxy;

import com.example.common.proxy.config.FeignConfig;
import com.example.common.proxy.user.payload.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(name = "phong-cert-servive", url = "NOT_USED" // template
		,configuration = FeignConfig.class)
public interface CertServiceProxy {

	@GetMapping(value = "/profile/find-by-username",
			headers = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserProfileResponse> findAuthorByUserName(
			URI uri,
			@RequestParam String username
	);
}
