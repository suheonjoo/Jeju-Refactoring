package com.capstone.jejuRefactoring.application.priority;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.capstone.jejuRefactoring.common.exception.spot.LocationGroupNotFoundException;
import com.capstone.jejuRefactoring.common.exception.spot.LocationNotFoundException;
import com.capstone.jejuRefactoring.domain.priority.dto.request.PriorityWeightDto;
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
public class PriorityFacade {

	private final SpotService spotService;
	private final PriorityService priorityService;

	public SpotPageWithPictureTagsResponse getSpotWithPictureTagsOrderByRank(Long memberId, PriorityWeightDto priorityWeightDto, String direction, Pageable pageable) {
		List<Location> locations = findLocationsByLocationGroup(direction);
		List<Long> spotIdsBySpotLocations = spotService.getBySpotLocations(locations);
		List<Long> spotIdsOrderByRank = priorityService.updateMemberSpotScore(memberId, spotIdsBySpotLocations, priorityWeightDto);
		return spotService.getSpotWithPictureTagLimit3(spotIdsOrderByRank, locations, pageable);
	}

	public SpotForRouteRecommendResponse getTenSpotsWithPictureTagsOrderByRankPerLocations(List<String> stringLocations) {
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




}
