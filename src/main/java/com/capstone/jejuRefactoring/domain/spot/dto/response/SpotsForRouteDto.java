package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotsForRouteDto {

	private Long wishListId;

	private List<SpotForRouteDto> spotForRouteDtos;

	public static SpotsForRouteDto of(Long wishListId, List<SpotForRouteDto> spotForRouteDtos) {
		return SpotsForRouteDto.builder()
			.wishListId(wishListId)
			.spotForRouteDtos(spotForRouteDtos)
			.build();
	}
}
