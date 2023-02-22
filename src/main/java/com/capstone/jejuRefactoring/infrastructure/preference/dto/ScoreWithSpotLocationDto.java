package com.capstone.jejuRefactoring.infrastructure.preference.dto;

import com.capstone.jejuRefactoring.domain.spot.Location;

import lombok.Getter;

@Getter
public class ScoreWithSpotLocationDto {

	private Double viewScore;
	private Double priceScore;
	private Double facilityScore;
	private Double surroundScore;
	private Location location;
	private Long spotId;

	public ScoreWithSpotLocationDto(Double viewScore, Double priceScore, Double facilityScore,
		Double surroundScore, Location location, Long spotId) {
		this.viewScore = viewScore;
		this.priceScore = priceScore;
		this.facilityScore = facilityScore;
		this.surroundScore = surroundScore;
		this.location = location;
		this.spotId = spotId;
	}
}
