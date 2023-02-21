package com.capstone.jejuRefactoring.application.preference;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.capstone.jejuRefactoring.common.exception.spot.LocationGroupNotFoundException;
import com.capstone.jejuRefactoring.common.exception.spot.LocationNotFoundException;
import com.capstone.jejuRefactoring.domain.preference.dto.request.PriorityWeightDto;
import com.capstone.jejuRefactoring.domain.preference.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.preference.service.PreferenceService;
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
public class PreferenceFacade {

	private final SpotService spotService;
	private final PreferenceService preferenceService;

	@Transactional
	public SpotPageWithPictureTagsResponse getSpotWithPictureTagsOrderByRank(Long memberId,
		PriorityWeightDto priorityWeightDto, String direction, Pageable pageable) {
		List<Location> locations = findLocationsByLocationGroup(direction);
		List<Long> spotIdsBySpotLocations = spotService.getBySpotLocations(locations);
		SpotIdsWithPageInfoDto spotIdsWithPageInfoDto = preferenceService.updateMemberSpotScore(memberId,
			spotIdsBySpotLocations, priorityWeightDto, pageable);
		return spotService.getSpotWithPictureTagLimit3(spotIdsWithPageInfoDto, locations);
	}

	public SpotForRouteRecommendResponse getTenSpotsWithPictureTagsOrderByRankPerLocations(
		List<String> stringLocations) {
		List<Location> locations = findLocationsByStringLocations(stringLocations);
		Category category = preferenceService.getHighestCategoryByLocations(locations);
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
		return preferenceService.flipSpotLike(spotId, memberId, 1);
	}

}
