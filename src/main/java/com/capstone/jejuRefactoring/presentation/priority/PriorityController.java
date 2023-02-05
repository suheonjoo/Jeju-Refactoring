package com.capstone.jejuRefactoring.presentation.priority;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.priority.PriorityFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.presentation.auth.LoginUser;
import com.capstone.jejuRefactoring.presentation.priority.request.PriorityRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class PriorityController {

	private final PriorityFacade priorityFacade;

	@PostMapping("/priority")
	public ResponseEntity<CommonResponse> showPriority(@RequestBody final PriorityRequest priorityRequest,
		Pageable pageable, @LoginUser Member member) {

		SpotPageWithPictureTagsResponse spotPageWithPictureTagsResponse = priorityFacade.getSpotWithPictureTagsOrderByRank(
			member.getId(), priorityRequest.toPriorityWeightDto(),
			priorityRequest.getDirection(), pageable);
		return ResponseEntity.ok()
			.body(CommonResponse.success(spotPageWithPictureTagsResponse));
	}

	@GetMapping("/recommendTop10")
	public ResponseEntity<CommonResponse> showRecommendTop10(@RequestParam final List<String> stringLocations) {

		SpotForRouteRecommendResponse spotForRouteRecommendResponse = priorityFacade.getTenSpotsWithPictureTagsOrderByRankPerLocations(
			stringLocations);
		return ResponseEntity.ok()
			.body(CommonResponse.success(spotForRouteRecommendResponse));
	}

}
