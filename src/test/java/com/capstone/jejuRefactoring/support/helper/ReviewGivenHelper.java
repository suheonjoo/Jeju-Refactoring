package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public class ReviewGivenHelper {

	public static Review givenReview(Spot spot) {
		return Review.builder()
			.spot(spot)
			.content("관광지가 너무 좋았어요")
			.build();
	}
}
