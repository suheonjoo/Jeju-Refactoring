package com.capstone.jejuRefactoring.unit.application.review;

import static com.capstone.jejuRefactoring.support.helper.ReviewGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;

import com.capstone.jejuRefactoring.application.review.ReviewFacade;
import com.capstone.jejuRefactoring.domain.review.dto.request.ReviewPageResponse;
import com.capstone.jejuRefactoring.domain.review.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ReviewFacadeTest {

	@InjectMocks
	ReviewFacade reviewFacade;
	@Mock
	ReviewService reviewService;

	@Test
	public void 특정_관광지_리뷰들_보여주기() throws Exception{
	    //given
		PageRequest pageRequest = PageRequest.of(0, 3);
		given(reviewService.getReviewsBySpotId(any(), any()))
			.willReturn(givenReviewPageResponse(pageRequest, 3));

	    //when
		ReviewPageResponse result = reviewFacade.getReviewsBySpotId(1l, pageRequest);

		//then
		assertThat(result.isHasNext()).isFalse();
		assertThat(result.getReviews().size()).isEqualTo(3);
	}
}
