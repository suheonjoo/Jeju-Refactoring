package com.capstone.jejuRefactoring.common.exception.spot;

import org.springframework.http.HttpStatus;

public class LocationNotFoundException extends SpotException{

	private static final String MESSAGE = "존재하지 않는 Location 입니다.";
	private static final String CODE = "LOCATION-400";

	public LocationNotFoundException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}