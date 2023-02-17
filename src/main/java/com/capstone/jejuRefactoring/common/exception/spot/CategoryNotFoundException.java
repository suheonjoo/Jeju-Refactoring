package com.capstone.jejuRefactoring.common.exception.spot;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends SpotException {

	private static final String MESSAGE = "존재하지 않는 Category 입니다.";
	private static final String CODE = "CATEGORY-400";

	public CategoryNotFoundException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}