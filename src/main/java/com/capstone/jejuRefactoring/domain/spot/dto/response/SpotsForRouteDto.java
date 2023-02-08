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

	private List<SpotWithPictureTagDto> spotWithPictureTagDtos;

	public static SpotsForRouteDto of(Long wishListId, List<SpotWithPictureTagDto> spotWithPictureTagDtos) {
		return SpotsForRouteDto.builder()
			.wishListId(wishListId)
			.spotWithPictureTagDtos(spotWithPictureTagDtos)
			.build();
	}
}
