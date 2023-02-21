package com.capstone.jejuRefactoring.domain.spot.dto.response;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpotWithCategoryScoreAndPictureTagUrlDto {

	private Long id;

	private String name;

	private String address;

	@Lob
	private String description;

	private Location location;

	private Double categoryScore;

	private PictureTagUrlDto pictureUrl;

	public static SpotWithCategoryScoreAndPictureTagUrlDto from(SpotWithCategoryScoreDto spotWithCategoryScoreDto) {
		return SpotWithCategoryScoreAndPictureTagUrlDto.builder()
			.id(spotWithCategoryScoreDto.getId())
			.name(spotWithCategoryScoreDto.getName())
			.address(spotWithCategoryScoreDto.getAddress())
			.description(spotWithCategoryScoreDto.getDescription())
			.location(spotWithCategoryScoreDto.getLocation())
			.categoryScore(spotWithCategoryScoreDto.getCategoryScore())
			.build();
	}

}
