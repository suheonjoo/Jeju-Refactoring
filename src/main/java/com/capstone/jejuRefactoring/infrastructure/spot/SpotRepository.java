package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SpotRepository implements com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository {

	private final SpotJpaRepository spotJpaRepository;
	private final SpotQuerydslRepository spotQuerydslRepository;

	@Override
	public Optional<Spot> findById(Long spotId) {
		return spotJpaRepository.findById(spotId);
	}

	@Override
	public List<Spot> findBySpotIdsWithFetchJoin(List<Long> spotIds) {
		return spotJpaRepository.findBySpotIdsWithFetchJoin(spotIds);
	}

	@Override
	public List<Long> findAllSpotIds() {
		return spotJpaRepository.findAllSpotId();
	}

	@Override
	public List<Spot> findByLocationsAndSpotIds(List<Long> spotIds, List<Location> locations) {
		return spotQuerydslRepository.findByLocationsAndSpotIds(spotIds, locations);
	}

	@Override
	public List<Long> findSpotIdsByLocations(List<Location> locations) {
		return spotJpaRepository.findSpotIdsByLocations(locations);
	}

	@Override
	public List<SpotWithCategoryScoreDto> findWithCategoryScoreByLocation(List<Location> locations, Category category) {
		return spotQuerydslRepository.findWithCategoryScoreByLocation(locations, category);
	}

	@Override
	public Slice<SpotPageResponse> findPageBySpotName(String spotName, Long lastSpotId, Pageable pageable) {
		return spotQuerydslRepository.findPageBySpotName(spotName, lastSpotId, pageable);
	}

}
