package com.example.gateway.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BaseController {
	public BaseController() {
	}

	protected String getUserName() {
		UserDetails userDetails = this.getPrincipal();
		return Objects.nonNull(userDetails) ? userDetails.getUsername() : null;
	}

	protected UserDetails getPrincipal() {
		Authentication authentication = this.getAuthentication();
		return Objects.nonNull(authentication) ? (UserDetails) authentication.getPrincipal() : null;
	}

	protected List<String> getAuthorities() {
		Authentication authentication = this.getAuthentication();
		return Objects.nonNull(authentication) && Objects.nonNull(authentication.getAuthorities()) ? (List) authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()) : Collections.emptyList();
	}

	protected Collection<? extends GrantedAuthority> getRoles() {
		Authentication authentication = this.getAuthentication();
		return (Collection) (Objects.nonNull(authentication) && Objects.nonNull(authentication.getAuthorities()) ? authentication.getAuthorities() : Collections.emptyList());
	}

	protected Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
