package com.capstone.jejuRefactoring.infrastructure.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

	private final ReviewQuerydslRepository reviewQuerydslRepository;

	@Override
	public Slice<Review> findReviewsBySpotId(Long spotId, Pageable pageable) {
		return reviewQuerydslRepository.findPageBySpotId(spotId, pageable);
	}

}
