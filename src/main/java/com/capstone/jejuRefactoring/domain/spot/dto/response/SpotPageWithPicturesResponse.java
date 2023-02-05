package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpotPageWithPicturesResponse {

	private final boolean hasNext;
	private final List<SpotWithPicturesDto> spotWithPicturesDtos;

	private SpotPageWithPicturesResponse(final boolean hasNext, final List<SpotWithPicturesDto> spotWithPicturesDtos) {
		this.hasNext = hasNext;
		this.spotWithPicturesDtos = spotWithPicturesDtos;
	}

	public static SpotPageWithPicturesResponse of(boolean hasNext, List<SpotWithPicturesDto> spotWithPicturesDtos) {
		return SpotPageWithPicturesResponse.builder()
			.hasNext(hasNext)
			.spotWithPicturesDtos(spotWithPicturesDtos)
			.build();
	}
}
