package com.capstone.jejuRefactoring.domain.preference.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeFlipResponse {

	private int likeCount;
	private boolean like;

	public static LikeFlipResponse of(int likeCount, boolean like) {
		return LikeFlipResponse.builder()
			.likeCount(likeCount)
			.like(like)
			.build();
	}

}
