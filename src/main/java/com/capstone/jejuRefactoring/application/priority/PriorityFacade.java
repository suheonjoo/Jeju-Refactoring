package com.capstone.jejuRefactoring.application.priority;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.capstone.jejuRefactoring.common.exception.spot.LocationGroupNotFoundException;
import com.capstone.jejuRefactoring.common.exception.spot.LocationNotFoundException;
import com.capstone.jejuRefactoring.domain.priority.dto.request.PriorityWeightDto;
import com.capstone.jejuRefactoring.domain.priority.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.priority.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.priority.service.PriorityService;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.LocationGroup;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotForRouteRecommendResponse;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageWithPictureTagsResponse;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriorityFacade {

	private final SpotService spotService;
	private final PriorityService priorityService;

	@Transactional
	public SpotPageWithPictureTagsResponse getSpotWithPictureTagsOrderByRank(Long memberId,
		PriorityWeightDto priorityWeightDto, String direction, Pageable pageable) {
		List<Location> locations = findLocationsByLocationGroup(direction);
		List<Long> spotIdsBySpotLocations = spotService.getBySpotLocations(locations);
		SpotIdsWithPageInfoDto spotIdsWithPageInfoDto = priorityService.updateMemberSpotScore(memberId,
			spotIdsBySpotLocations, priorityWeightDto, pageable);
		return spotService.getSpotWithPictureTagLimit3(spotIdsWithPageInfoDto, locations);
	}

	public SpotForRouteRecommendResponse getTenSpotsWithPictureTagsOrderByRankPerLocations(
		List<String> stringLocations) {
		List<Location> locations = findLocationsByStringLocations(stringLocations);
		Category category = priorityService.getHighestCategoryByLocations(locations);
		return spotService.getSpotWithPictureTagPerLocations(locations, category);
	}

	private List<Location> findLocationsByLocationGroup(String direction) {
		if (!StringUtils.hasText(direction)) {
			throw new LocationGroupNotFoundException();
		}
		return LocationGroup.getLocations(direction);
	}

	private List<Location> findLocationsByStringLocations(List<String> locations) {
		if (locations.isEmpty()) {
			throw new LocationNotFoundException();
		}
		return Location.getLocations(locations);
	}

	@Transactional
	public LikeFlipResponse flipSpotLike(Long spotId, Long memberId) {
		return priorityService.flipSpotLike(spotId, memberId, 1);
	}

}
