package com.example.common.proxy.config.error;

import com.example.common.proxy.controller.ErrorController;
import org.springframework.context.annotation.Import;

@Import(ErrorController.class)
public class GlobalExceptionHandling {
}
