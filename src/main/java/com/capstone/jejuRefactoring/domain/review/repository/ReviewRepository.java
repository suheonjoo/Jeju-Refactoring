package com.capstone.jejuRefactoring.domain.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.review.Review;

public interface ReviewRepository {
	Slice<Review> findReviewsBySpotId(Long spotId, Pageable pageable);
}
