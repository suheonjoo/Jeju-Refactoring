package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpotPageWithPictureTagsResponse {

	private final boolean hasNext;
	private final List<SpotWithPictureTagsDto> spotWithPictureTagsDtos;

	private SpotPageWithPictureTagsResponse(final boolean hasNext, final List<SpotWithPictureTagsDto> spotWithPictureTagsDtos) {
		this.hasNext = hasNext;
		this.spotWithPictureTagsDtos = spotWithPictureTagsDtos;
	}

	public static SpotPageWithPictureTagsResponse of(boolean hasNext, List<SpotWithPictureTagsDto> spotWithPictureTagsDtos) {
		return SpotPageWithPictureTagsResponse.builder()
			.hasNext(hasNext)
			.spotWithPictureTagsDtos(spotWithPictureTagsDtos)
			.build();
	}
}
