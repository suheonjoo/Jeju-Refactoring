package com.capstone.jejuRefactoring.domain.review.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.review.Review;

public class ReviewPageResponse {

	private final boolean hasNext;
	private final List<ReviewResponse> reviews;

	private ReviewPageResponse(final boolean hasNext, final List<ReviewResponse> reviews) {
		this.hasNext = hasNext;
		this.reviews = reviews;
	}

	public static ReviewPageResponse of(final Slice<Review> slice, final Long spotId) {
		final List<ReviewResponse> reviews = slice.getContent()
			.stream()
			.map(review -> ReviewResponse.of(review, spotId))
			.collect(Collectors.toList());
		return new ReviewPageResponse(slice.hasNext(), reviews);
	}

}
