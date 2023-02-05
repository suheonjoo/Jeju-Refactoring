package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotResponse {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	private ScoreResponse scoreResponse;

	private SpotResponse(Long id, String name, String address, String description,
		Location location, ScoreResponse scoreResponse) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.location = location;
		this.scoreResponse = scoreResponse;
	}

	public static SpotResponse from(Spot spot) {
		return SpotResponse.builder()
			.id(spot.getId())
			.name(spot.getName())
			.address(spot.getAddress())
			.description(spot.getDescription())
			.location(spot.getLocation())
			.build();
	}

	public static SpotResponse of(SpotResponse spotResponse, ScoreResponse scoreResponse) {
		return SpotResponse.builder()
			.id(spotResponse.getId())
			.name(spotResponse.getName())
			.address(spotResponse.getAddress())
			.description(spotResponse.getDescription())
			.location(spotResponse.getLocation())
			.scoreResponse(scoreResponse)
			.build();
	}
}
