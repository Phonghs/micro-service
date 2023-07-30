package com.example.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {
	@GetMapping("/certservice")
	public String certServiceFallback() {
		return "This is a fallback for web-api service.";
	}

}
