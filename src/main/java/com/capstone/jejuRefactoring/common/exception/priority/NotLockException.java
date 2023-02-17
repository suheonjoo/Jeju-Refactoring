package com.capstone.jejuRefactoring.common.exception.priority;

import org.springframework.http.HttpStatus;

public class NotLockException extends PriorityException {

	private static final String MESSAGE = "존재하지 않는 Redis-Lock 입니다.";
	private static final String CODE = "Redis-Lock-400";

	public NotLockException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
