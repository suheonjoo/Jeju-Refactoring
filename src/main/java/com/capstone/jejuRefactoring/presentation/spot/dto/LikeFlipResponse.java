package com.capstone.jejuRefactoring.presentation.spot.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeFlipResponse {

	private int likeCount;
	private boolean like;

	private LikeFlipResponse() {
	}




	public static LikeFlipResponse of(int likeCount, boolean like) {
		return LikeFlipResponse.builder()
			.likeCount(likeCount)
			.like(like)
			.build();
	}

}
