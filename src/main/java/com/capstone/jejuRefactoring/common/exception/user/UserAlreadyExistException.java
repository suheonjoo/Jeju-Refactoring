package com.capstone.jejuRefactoring.common.exception.user;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends UserException {
	private static final String MESSAGE = "이미 존재하는 회원입니다.";
	private static final String CODE = "LOGIN-400";

	public UserAlreadyExistException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}