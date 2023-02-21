package com.capstone.jejuRefactoring.domain.preference.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.ScoreWithSpotLocationDto;

public interface ScoreRepository {

	List<Score> findBySpotIds(List<Long> spotIds);

	Score findBySpotId(Long spotId);

	List<ScoreWithSpotLocationDto> findScoreBySpotLocations(List<Location> locations);
}
