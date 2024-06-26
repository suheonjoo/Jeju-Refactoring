package com.capstone.jejuRefactoring.application.review;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.review.dto.request.ReviewPageResponse;
import com.capstone.jejuRefactoring.domain.review.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewFacade {

	private final ReviewService reviewService;

	public ReviewPageResponse getReviewsBySpotId(final Long spotId, Long lastReviewId, Pageable pageable) {
		return reviewService.getReviewsBySpotId(spotId,lastReviewId, pageable);
	}
}
