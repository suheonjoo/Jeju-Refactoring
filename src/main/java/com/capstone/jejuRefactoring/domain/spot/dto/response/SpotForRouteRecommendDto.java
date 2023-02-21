package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.spot.Location;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotForRouteRecommendDto {

	private Location location;

	private List<SpotWithCategoryScoreAndPictureTagUrlDto> spotWithCategoryScoreAndPictureTagUrlDtos;

	public static SpotForRouteRecommendDto from(Location location,
		List<SpotWithCategoryScoreAndPictureTagUrlDto> spotWithCategoryScoreAndPictureTagUrlDtos) {
		return SpotForRouteRecommendDto.builder()
			.location(location)
			.spotWithCategoryScoreAndPictureTagUrlDtos(spotWithCategoryScoreAndPictureTagUrlDtos)
			.build();
	}
}
