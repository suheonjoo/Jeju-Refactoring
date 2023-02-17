package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;

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

	private List<SpotWithCategoryScoreDto> spotWithCategoryScoreDtos;

	public static SpotForRouteRecommendDto from(Location location,
		List<SpotWithCategoryScoreDto> spotWithCategoryScoreDtos) {
		return SpotForRouteRecommendDto.builder()
			.location(location)
			.spotWithCategoryScoreDtos(spotWithCategoryScoreDtos)
			.build();
	}
}
