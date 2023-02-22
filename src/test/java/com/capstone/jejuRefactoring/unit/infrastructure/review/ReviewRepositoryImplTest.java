package com.capstone.jejuRefactoring.unit.infrastructure.review;

import static com.capstone.jejuRefactoring.support.helper.PictureGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.ReviewGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.infrastructure.review.ReviewJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.review.ReviewRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class ReviewRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	ReviewRepositoryImpl reviewRepository;
	@Autowired
	ReviewJpaRepository reviewJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;

	@Test
	public void 페이징된_관광지리뷰찾기_다음페이지없음() throws Exception{
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		reviewJpaRepository.saveAll(List.of(
				givenReview(spotId), givenReview(spotId), givenReview(spotId)
			)
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 20);
		Slice<Review> target =
			reviewRepository.findReviewsBySpotId(spotId, pageRequest);

		//then
		assertThat(target.getContent().size()).isEqualTo(3);
		assertThat(target.hasNext()).isFalse();
	}

	@Test
	public void 페이징된_관광지리뷰찾기_다음페이지있음() throws Exception{
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		reviewJpaRepository.saveAll(List.of(
				givenReview(spotId), givenReview(spotId), givenReview(spotId)
			)
		);

		//when
		PageRequest pageRequest = PageRequest.of(0, 1);
		Slice<Review> target =
			reviewRepository.findReviewsBySpotId(spotId, pageRequest);

		//then
		assertThat(target.getContent().size()).isEqualTo(1);
		assertThat(target.hasNext()).isTrue();
	}


}
