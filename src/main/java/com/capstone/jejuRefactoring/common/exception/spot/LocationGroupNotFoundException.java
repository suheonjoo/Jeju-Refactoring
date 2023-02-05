package com.capstone.jejuRefactoring.common.exception.spot;

import org.springframework.http.HttpStatus;

public class LocationGroupNotFoundException extends SpotException{

	private static final String MESSAGE = "존재하지 않는 LocationGroup 입니다.";
	private static final String CODE = "LOCATIONGROUP-400";

	public LocationGroupNotFoundException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
