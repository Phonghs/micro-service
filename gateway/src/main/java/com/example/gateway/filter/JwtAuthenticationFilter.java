package com.example.gateway.filter;

import com.example.gateway.exception.TokenExtractException;
import com.example.gateway.service.UserService;
import com.example.gateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
public class JwtAuthenticationFilter implements GatewayFilter  {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserService userService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		List<String> endPoints = Arrays.asList("/register", "/login");
		Predicate<ServerHttpRequest> isApiSecured = r -> endPoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));
		if(isApiSecured.test(exchange.getRequest())) {
			try {
				String token = this.extractJWTToken(exchange.getRequest());
				if (!StringUtils.isEmpty(token)) {
					if(jwtUtils.isTokenValid(token,userService.findByUserName(jwtUtils.getUsernameFromToken(token)))){
						exchange.getRequest().mutate().header("user-name",jwtUtils.getUsernameFromToken(token)).build();
						return chain.filter(exchange);
					}
				}
				return this.onError(exchange, "Invalid token");
			} catch (Exception ex) {
				log.error(ex.toString());
				return this.onError(exchange, "Invalid token");
			}
		}
		return chain.filter(exchange);
	}
	private String extractJWTToken(ServerHttpRequest request) {
		if (!request.getHeaders().containsKey("Authorization")) {
			throw new TokenExtractException("Authorization header is missing");
		}

		List<String> headers = request.getHeaders().get("Authorization");
		if (headers.isEmpty()) {
			throw new TokenExtractException("Authorization header is empty");
		}

		String credential = headers.get(0).trim();
		String[] components = credential.split("\\s");
		return components[0].trim();
	}

	private Mono<Void> onError(ServerWebExchange exchange, String err) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return response.setComplete();
	}

	private String formatErrorMsg(String msg) {
		return String.format("error=\"https://localhost:8888/login" + "error_description=\"%s\" ", msg);
	}
}
