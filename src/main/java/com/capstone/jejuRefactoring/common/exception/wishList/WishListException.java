package com.capstone.jejuRefactoring.common.exception.wishList;

import org.springframework.http.HttpStatus;

import com.capstone.jejuRefactoring.common.exception.ApplicationException;

public abstract class WishListException extends ApplicationException {

	protected WishListException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}