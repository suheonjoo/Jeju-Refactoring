package com.capstone.jejuRefactoring.domain.spot;

import java.util.Arrays;

import com.capstone.jejuRefactoring.common.exception.spot.CategoryNotFoundException;

public enum Category {
	VIEW("뷰"),
	PRICE("가격"),
	FACILITY("편의시설"),
	SURROUND("서비스"),
	ALL("전체");

	private String name;

	Category(String name) {
		this.name = name;
	}

	public static Category fromName(String name) {
		return Arrays.stream(Category.values())
			.filter(category -> category.name.equals(name))
			.findAny()
			.orElseThrow(() -> new CategoryNotFoundException());

	}

}
