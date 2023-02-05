package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SpotRepositoryImpl implements SpotRepository {

	private final SpotJpaRepository spotJpaRepository;
	private final SpotQuerydslRepository spotQuerydslRepository;


	@Override
	public Optional<Spot> findById(Long spotId) {
		return spotJpaRepository.findById(spotId);
	}

	@Override
	public List<Spot> findBySpotIds(List<Long> spotIds) {
		return spotJpaRepository.findBySpotIds(spotIds);
	}

	@Override
	public List<Long> findAllSpotIds() {
		return spotJpaRepository.findAllSpotId();
	}

	@Override
	public Slice<Spot> findPageBySpotIds(List<Long> spotIds, List<Location> locations, Pageable pageable) {
		return spotQuerydslRepository.findPageBySpotIds(spotIds, locations, pageable);
	}

	@Override
	public List<Long> findBySpotLocations(List<Location> locations) {
		return spotJpaRepository.findBySpotLocations(locations);
	}

	@Override
	public List<Spot> findByLocationAndCategory(Location location, Category category) {
		return spotQuerydslRepository.findByLocationAndCategory(location,category);
	}

}
