package com.capstone.jejuRefactoring.domain.review.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.review.dto.request.ReviewPageResponse;
import com.capstone.jejuRefactoring.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

	private final ReviewRepository reviewRepository;

	public ReviewPageResponse getReviewsBySpotId(final Long spotId,Long lastReviewId, Pageable pageable) {
		Slice<Review> page = reviewRepository.findOpReviewsBySpotId(spotId,lastReviewId, pageable);
		return ReviewPageResponse.of(page, spotId);
	}

}
