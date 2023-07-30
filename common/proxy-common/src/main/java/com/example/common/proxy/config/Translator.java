package com.example.common.proxy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Component
public class Translator {
	private static ResourceBundleMessageSource messageSource;

	@Autowired
	Translator(ResourceBundleMessageSource messageSource) {
		Translator.messageSource = messageSource;
	}

	public static String toLocale(String msgCode) {
		if (Objects.isNull(messageSource)) {
			return null;
		} else {
			Locale locale = LocaleContextHolder.getLocale();
			return messageSource.getMessage(msgCode, null, locale);
		}
	}

	public static String toLocale(String msgCode, String defaultMsg) {
		if (Objects.isNull(messageSource)) {
			return defaultMsg;
		} else {
			Locale locale = LocaleContextHolder.getLocale();
			return messageSource.getMessage(msgCode, null, defaultMsg, locale);
		}
	}

	public static String toLocale(String msgCode, Object[] args) {
		if (Objects.isNull(messageSource)) {
			return null;
		} else {
			Locale locale = LocaleContextHolder.getLocale();
			return messageSource.getMessage(msgCode, args, locale);
		}
	}
}
