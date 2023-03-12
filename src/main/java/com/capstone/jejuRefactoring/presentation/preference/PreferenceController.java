package com.capstone.jejuRefactoring.presentation.preference;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.preference.PreferenceFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.preference.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.presentation.auth.LoginUser;
import com.capstone.jejuRefactoring.presentation.preference.request.PriorityRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/spots")
@RequiredArgsConstructor
public class PreferenceController {

	private final PreferenceFacade preferenceFacade;

	@PostMapping("/priority")
	public ResponseEntity<CommonResponse> showPriority(@RequestBody final PriorityRequest priorityRequest,
		Pageable pageable, @LoginUser Member member) {

		SpotPageWithPictureTagsResponse spotPageWithPictureTagsResponse = preferenceFacade.getSpotWithPictureTagsOrderByRank(
			member.getId(), priorityRequest.toPriorityWeightDto(),
			priorityRequest.getDirection(), pageable);
		return ResponseEntity.ok()
			.body(CommonResponse.success(spotPageWithPictureTagsResponse));
	}

	@GetMapping("/recommendTop10")
	public ResponseEntity<CommonResponse> showRecommendTop10(@RequestParam final List<String> stringLocations) {

		SpotForRouteRecommendResponse spotForRouteRecommendResponse = preferenceFacade.getTenSpotsWithPictureTagsOrderByRankPerLocations(
			stringLocations);
		return ResponseEntity.ok()
			.body(CommonResponse.success(spotForRouteRecommendResponse));
	}

	@PutMapping("/{spotId}/like")
	public ResponseEntity<LikeFlipResponse> flipSpotLike(@PathVariable final Long spotId,
		@LoginUser Member member) {
		LikeFlipResponse likeFlipResponse = preferenceFacade.flipSpotLike(spotId, member.getId());
		return ResponseEntity.ok(likeFlipResponse);
	}

}
