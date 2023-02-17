package com.capstone.jejuRefactoring.common.exception.spot;

import org.springframework.http.HttpStatus;

public class SpotNotFoundException extends SpotException {

	private static final String MESSAGE = "존재하지 않는 Spot 입니다.";
	private static final String CODE = "SPOT-400";

	public SpotNotFoundException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
