package com.capstone.jejuRefactoring.common.exception.wishList;

import org.springframework.http.HttpStatus;

public class SameWishListNameException extends WishListException {

	private static final String MESSAGE = "이미 존재하는 WishList 이름 입니다.";
	private static final String CODE = "WishList-400";

	public SameWishListNameException() {
		super(CODE, HttpStatus.BAD_REQUEST, MESSAGE);
	}
}
