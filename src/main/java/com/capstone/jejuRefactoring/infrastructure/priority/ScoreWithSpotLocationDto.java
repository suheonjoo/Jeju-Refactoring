package com.capstone.jejuRefactoring.infrastructure.priority;

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



}
