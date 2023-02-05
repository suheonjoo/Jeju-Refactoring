package com.capstone.jejuRefactoring.presentation.review;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.review.ReviewFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.domain.review.dto.request.ReviewPageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewFacade reviewFacade;

	@GetMapping("/{spotId}/reviews")
	public ResponseEntity<CommonResponse> showReviews(@PathVariable final Long spotId, Pageable pageable) {
		ReviewPageResponse reviewsBySpotId = reviewFacade.getReviewsBySpotId(spotId, pageable);
		return ResponseEntity.ok()
			.body(CommonResponse.success(reviewsBySpotId));
	}

}
