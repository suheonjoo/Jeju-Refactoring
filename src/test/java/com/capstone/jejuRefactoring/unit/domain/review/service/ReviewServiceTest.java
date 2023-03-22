package com.capstone.jejuRefactoring.unit.domain.review.service;

import static com.capstone.jejuRefactoring.support.helper.PictureGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.ReviewGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.preference.service.PreferenceService;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.review.dto.request.ReviewPageResponse;
import com.capstone.jejuRefactoring.domain.review.repository.ReviewRepository;
import com.capstone.jejuRefactoring.domain.review.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

	@InjectMocks
	ReviewService reviewService;
	@Mock
	ReviewRepository reviewRepository;

	@Test
	public void 페이징된_관광지_리뷰_가져오기() throws Exception{
	    //given
		List<Review> reviews = List.of(givenReview(1l), givenReview(1l), givenReview(1l));
		PageRequest pageRequest = PageRequest.of(0, 20);
		Slice<Review> slice = RepositorySupport.toSlice(reviews, pageRequest);
		given(reviewRepository.findOpReviewsBySpotId(any(), any(),any())).willReturn(slice);

	    //when
		ReviewPageResponse result = reviewService.getReviewsBySpotId(1l, 0l,pageRequest);

		//then
		assertThat(result.isHasNext()).isFalse();
		assertThat(result.getReviews().size()).isEqualTo(3);
	}

}
