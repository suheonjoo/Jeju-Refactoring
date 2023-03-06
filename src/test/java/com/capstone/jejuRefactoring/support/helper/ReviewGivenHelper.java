package com.capstone.jejuRefactoring.support.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.review.dto.request.ReviewPageResponse;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public class ReviewGivenHelper {

	public static Review givenReview(Long spotId) {
		return Review.builder()
			.spot(Spot.builder().id(spotId).build())
			.content("관광지가 너무 좋았어요")
			.build();
	}

	public static ReviewPageResponse givenReviewPageResponse(Pageable pageable, int count) {
		List<Review> reviews = new ArrayList<>();
		LongStream.range(1, 1 + count).forEach(i -> reviews.add(givenReview(i)));
		return ReviewPageResponse.of(RepositorySupport.toSlice(reviews, pageable), 1l);
	}
}
