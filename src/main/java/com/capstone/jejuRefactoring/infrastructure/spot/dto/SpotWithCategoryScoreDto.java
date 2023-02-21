package com.capstone.jejuRefactoring.infrastructure.spot.dto;

import com.capstone.jejuRefactoring.domain.spot.Location;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class SpotWithCategoryScoreDto implements Comparable<SpotWithCategoryScoreDto> {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	private Double categoryScore;

	public SpotWithCategoryScoreDto(Long id, String name, String address, String description,
		Location location, Double categoryScore) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.location = location;
		this.categoryScore = categoryScore;
	}

	@Override
	public int compareTo(SpotWithCategoryScoreDto o) {
		return (int)(o.getCategoryScore() - this.getCategoryScore());
	}
}
