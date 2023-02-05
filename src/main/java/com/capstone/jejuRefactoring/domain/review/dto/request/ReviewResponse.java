package com.capstone.jejuRefactoring.domain.review.dto.request;

import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.spot.Spot;

import lombok.Getter;

@Getter
public class ReviewResponse {

	private Long id;
	private boolean spotMatch;
	private String content;

	private ReviewResponse(Long id, boolean spotMatch, String content) {
		this.id = id;
		this.spotMatch = spotMatch;
		this.content = content;
	}

	public static ReviewResponse of(final Review review, final Long spotId) {
		final Spot spot = review.getSpot();
		return new ReviewResponse(review.getId(), spot.isSameId(spotId), review.getContent());
	}
}
