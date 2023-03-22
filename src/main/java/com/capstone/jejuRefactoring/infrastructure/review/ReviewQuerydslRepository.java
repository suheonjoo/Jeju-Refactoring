package com.capstone.jejuRefactoring.infrastructure.review;

import static com.capstone.jejuRefactoring.common.support.RepositorySupport.*;
import static com.capstone.jejuRefactoring.domain.picture.QPicture.*;
import static com.capstone.jejuRefactoring.domain.review.QReview.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

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


	public Slice<Review> findOpPageBySpotId(Long spotId, Long lastReviewId, Pageable pageable) {
		List<Review> pictures = query
			.selectFrom(review)
			.where(
				ltReviewId(lastReviewId),
				review.spot.id.eq(spotId)
			)
			.orderBy(review.id.asc())
			.limit(pageable.getPageSize()+1)
			.fetch();
		return toSlice(pictures, pageable);
	}

	private BooleanExpression ltReviewId(Long reviewId) {
		if (reviewId == null) {
			return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
		}
		return review.id.gt(reviewId);
	}

}
