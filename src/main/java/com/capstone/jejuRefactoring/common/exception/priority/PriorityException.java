package com.capstone.jejuRefactoring.common.exception.priority;

import org.springframework.http.HttpStatus;

import com.capstone.jejuRefactoring.common.exception.ApplicationException;

public abstract class PriorityException extends ApplicationException {

	protected PriorityException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}
