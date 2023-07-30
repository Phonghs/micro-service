package com.example.gateway.security;

import com.example.gateway.filter.JwtAuthenticationFilter;
import com.example.gateway.security.oauth2.CustomOAuth2UserService;
import com.example.gateway.security.oauth2.SuccessHandleOAuth2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomOAuth2UserService oAuth2UserService;

	@Autowired
	private SuccessHandleOAuth2 successHandleOAuth2;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig() {
	}


	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/auth/login","auth/register").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/oauth/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.oauth2Login().userInfoEndpoint().userService(oAuth2UserService)
				.and()
				.successHandler(successHandleOAuth2);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }

}