package com.example.common.proxy.controller;

import com.example.common.proxy.model.ApiError;
import com.example.common.proxy.model.ApiResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController extends AbstractErrorController {

	@Getter
	@Value("${error.path:/error}")
	private String errorPath;

	private final ErrorAttributes errorAttributes;

	public ErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping(value = "${error.path:/error}")
	@ResponseBody
	public ApiResponse<?> error(HttpServletRequest request) {
		HttpStatus httpStatus = getStatus(request);
		WebRequest webRequest = new ServletWebRequest(request);
		Throwable throwable = errorAttributes.getError(webRequest);
		return ApiResponse.error(ApiError.buildApiError(throwable, httpStatus));
	}

}
