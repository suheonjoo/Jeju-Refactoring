package com.capstone.jejuRefactoring.domain.preference.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpotIdsWithPageInfoDto {

	private boolean hasNext;
	private List<Long> spotIds;

	public static SpotIdsWithPageInfoDto of(boolean hasNext, List<Long> spotIds) {
		return SpotIdsWithPageInfoDto.builder()
			.hasNext(hasNext)
			.spotIds(spotIds)
			.build();
	}

}
