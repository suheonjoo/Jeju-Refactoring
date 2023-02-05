package com.capstone.jejuRefactoring.domain.priority.dto.response;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.dto.response.ScoreResponse;

import jakarta.persistence.Lob;

public class SpotWithScoreResponse {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	private ScoreResponse scoreResponse;




}
