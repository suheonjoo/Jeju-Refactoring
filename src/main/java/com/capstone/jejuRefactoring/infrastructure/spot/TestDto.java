package com.capstone.jejuRefactoring.infrastructure.spot;

import java.net.SocketOption;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.Lob;
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
