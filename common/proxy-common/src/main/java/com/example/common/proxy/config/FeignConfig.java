package com.example.common.proxy.config;

import com.example.common.proxy.decoder.OpenFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(HttpMessageConvertersAutoConfiguration.class)
public class FeignConfig {
	@Bean
	public ErrorDecoder errorDecoder() {
		return new OpenFeignErrorDecoder();
	}

}
