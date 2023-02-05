package com.capstone.jejuRefactoring.common.exception.security;

import org.springframework.http.HttpStatus;

import com.capstone.jejuRefactoring.common.exception.ApplicationException;

public abstract class AuthException extends ApplicationException {

    protected AuthException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }

    protected AuthException(String errorCode, HttpStatus httpStatus, String message,Throwable cause) {
        super(errorCode, httpStatus, message,cause);
    }
}
