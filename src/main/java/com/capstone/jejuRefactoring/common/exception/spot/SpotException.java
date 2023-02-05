package com.capstone.jejuRefactoring.common.exception.spot;

import org.springframework.http.HttpStatus;

import com.capstone.jejuRefactoring.common.exception.ApplicationException;

public abstract class SpotException extends ApplicationException {

	protected SpotException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}
