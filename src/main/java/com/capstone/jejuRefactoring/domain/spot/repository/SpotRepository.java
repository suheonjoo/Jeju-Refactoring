package com.capstone.jejuRefactoring.domain.spot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public interface SpotRepository {

	Optional<Spot> findById(Long spotId);

	List<Spot> findBySpotIds(List<Long> spotIds);

	List<Long> findAllSpotIds();

	Slice<Spot> findPageBySpotIds(List<Long> spotIds, List<Location> location, Pageable pageable);

	List<Long> findBySpotLocations(List<Location> locations);

	List<Spot> findByLocationAndCategory(Location location, Category category);
}
