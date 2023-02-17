package com.capstone.jejuRefactoring.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

	private final String errorCode;
	private final HttpStatus httpStatus;
	private BindingResult errors;

	protected ApplicationException(String errorCode, HttpStatus httpStatus, String message) {
		super(message);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	protected ApplicationException(String errorCode, HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	protected ApplicationException(String errorCode, HttpStatus httpStatus, String message, BindingResult errors) {
		super(message);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errors = errors;
	}

	protected ApplicationException(String errorCode, HttpStatus httpStatus, String message, BindingResult errors,
		Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
		this.errors = errors;
	}

}

