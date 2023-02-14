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

	private Double CategoryScore;

	private PictureTagUrlDto pictureUrl = null;

	@Override
	public int compareTo(SpotWithCategoryScoreDto o) {
		return (int)(o.getCategoryScore() - this.getCategoryScore());
	}
}
