package com.example.gateway.config;

import com.example.gateway.filter.JwtAuthenticationFilter;
import okhttp3.OkHttpClient;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableHystrix
public class BeanConfig {

	private final JwtAuthenticationFilter filter;

	public BeanConfig(@Lazy JwtAuthenticationFilter filter) {
		this.filter = filter;
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

		return builder.routes()
				.route("phong-cert-service", r -> r.path("/api/cert/**")
						.filters(f -> f.rewritePath("/api/cert/(?<remains>.*)", "/${remains}")
								.filter(filter))
						.uri("lb://phong-cert-service/"))
				.route("phong-user-service",r -> r.path("/api/user/**")
						.filters(f -> f.rewritePath("api/user/(?<remains>.*)","/${remains}")
								.filter(filter))
						.uri("lb://phong-user-service/"))
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ServerCodecConfigurer serverCodecConfigurer() {
		return ServerCodecConfigurer.create();
	}

	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}
	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes();
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
