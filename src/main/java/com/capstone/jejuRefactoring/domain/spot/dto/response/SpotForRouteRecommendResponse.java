package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.spot.Category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotForRouteRecommendResponse {

	private Category category;

	private List<SpotForRouteRecommendDto> spotForRouteRecommendDtos;

	public static SpotForRouteRecommendResponse from(Category category,
		List<SpotForRouteRecommendDto> spotForRouteRecommendDtos) {
		return SpotForRouteRecommendResponse.builder()
			.category(category)
			.spotForRouteRecommendDtos(spotForRouteRecommendDtos)
			.build();
	}
}
