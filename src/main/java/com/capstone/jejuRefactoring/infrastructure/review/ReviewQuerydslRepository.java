package com.capstone.jejuRefactoring.infrastructure.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.review.QReview;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;

import static com.capstone.jejuRefactoring.common.support.RepositorySupport.*;
import static com.capstone.jejuRefactoring.domain.review.QReview.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewQuerydslRepository {

	private final JPAQueryFactory query;

	public Slice<Review> findPageBySpotId(Long spotId, Pageable pageable) {
		List<Review> reviews = query.selectFrom(review)
			.where(review.spot.id.eq(spotId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return toSlice(reviews, pageable);

	}

}
