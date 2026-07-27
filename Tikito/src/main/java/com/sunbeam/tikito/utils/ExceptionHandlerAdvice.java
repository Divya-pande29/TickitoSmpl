package com.sunbeam.tikito.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(Exception.class)
	public Resp<?> handleError(Exception e) {

		String msg = e.getClass().getName() + " : " + e.getMessage();

		return Resp.error(msg);

	}

}

