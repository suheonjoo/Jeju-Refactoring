package com.capstone.jejuRefactoring.common.exception.user;

import org.springframework.http.HttpStatus;

import com.capstone.jejuRefactoring.common.exception.ApplicationException;

public abstract class UserException extends ApplicationException {

	protected UserException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}
