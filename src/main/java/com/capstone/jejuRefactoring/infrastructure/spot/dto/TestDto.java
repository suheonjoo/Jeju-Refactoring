package com.capstone.jejuRefactoring.infrastructure.spot.dto;

import lombok.Data;

@Data
public class TestDto {

	private Long spotId;

	private Double CategoryScore;

	public TestDto(Long spotId, Double categoryScore) {
		this.spotId = spotId;
		CategoryScore = categoryScore;
	}
}
