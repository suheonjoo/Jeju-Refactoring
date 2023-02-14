package com.capstone.jejuRefactoring.infrastructure.priority;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.priority.Score;
import com.capstone.jejuRefactoring.domain.priority.repository.ScoreRepository;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.infrastructure.priority.dto.ScoreWithSpotLocationDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScoreRepositoryImpl implements ScoreRepository {

	private final ScoreJpaRepository scoreJpaRepository;
	private final ScoreQuerydslRepository scoreQuerydslRepository;

	@Override
	public List<Score> findBySpotIds(List<Long> spotIds) {
		return scoreJpaRepository.findBySpotIds(spotIds);
	}

	@Override
	public Score findBySpotId(Long spotId) {
		return scoreJpaRepository.findBySpotId(spotId);
	}

	@Override
	public List<ScoreWithSpotLocationDto> findScoreBySpotLocations(List<Location> locations) {
		return scoreQuerydslRepository.findScoreBySpotLocations(locations);
	}
}
