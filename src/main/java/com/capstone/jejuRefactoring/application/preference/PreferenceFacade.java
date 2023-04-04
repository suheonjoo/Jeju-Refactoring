package com.capstone.jejuRefactoring.application.preference;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.capstone.jejuRefactoring.common.exception.priority.NotLockException;
import com.capstone.jejuRefactoring.common.exception.spot.LocationGroupNotFoundException;
import com.capstone.jejuRefactoring.common.exception.spot.LocationNotFoundException;
import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
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
public class PreferenceFacade {

	private final SpotService spotService;
	private final PreferenceService preferenceService;
	private final RedissonClient redissonClient;

	@Transactional
	public SpotPageWithPictureTagsResponse getSpotWithPictureTagsOrderByRank(Long memberId,
		PriorityWeightDto priorityWeightDto, String direction, Pageable pageable) {
		List<Location> locations = findLocationsByLocationGroup(direction);
		List<Long> spotIdsBySpotLocations = spotService.getBySpotLocations(locations);
		SpotIdsWithPageInfoDto spotIdsWithPageInfoDto = preferenceService.updateMemberSpotScore(memberId,
			spotIdsBySpotLocations, priorityWeightDto, pageable);
		return spotService.getSpotWithPictureTagLimit3(spotIdsWithPageInfoDto, locations);
	}

	@Transactional(readOnly = true)
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

	public LikeFlipResponse flipSpotLike(Long spotId, Long memberId) {
		LikeFlipResponse likeFlipResponse;
		RLock lock = redissonClient.getLock(spotId.toString());
		try {
			validatedLock(lock.tryLock(15, 1, TimeUnit.SECONDS));
			likeFlipResponse = preferenceService.updateSpotLike(spotId, memberId);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
		return likeFlipResponse;
	}

	private void validatedLock(boolean available) {
		if (!available) {
			throw new NotLockException();
		}
	}

}
