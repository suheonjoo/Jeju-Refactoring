package com.capstone.jejuRefactoring.domain.priority.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.priority.Score;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.infrastructure.priority.ScoreWithSpotLocationDto;

public interface ScoreRepository {

	List<Score> findBySpotIds(List<Long> spotIds);

	Score findBySpotId(Long spotId);

	List<ScoreWithSpotLocationDto> findScoreBySpotLocations(List<Location> locations);
}
