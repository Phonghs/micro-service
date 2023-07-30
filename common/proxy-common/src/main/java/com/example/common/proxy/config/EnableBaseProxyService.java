package com.example.common.proxy.config;

import com.example.common.proxy.BaseProxyService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({BaseProxyService.class})
public @interface EnableBaseProxyService {

}
