package com.capstone.jejuRefactoring.unit.infrastructure.preference;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.preference.ScoreJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.ScoreRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.ScoreWithSpotLocationDto;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class ScoreRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	ScoreJpaRepository scoreJpaRepository;
	@Autowired
	ScoreRepositoryImpl scoreRepository;

	@Test
	public void 광광지아이디들에_해당하는_점수들_찾기() throws Exception {
		//given
		List<Spot> spots = spotJpaRepository.saveAll(List.of(givenSpot(), givenSpot(), givenSpot()));
		scoreJpaRepository.saveAll(List.of(
				givenScore(spots.get(0).getId()), givenScore(spots.get(1).getId()), givenScore(spots.get(2).getId())
			)
		);

		//when
		List<Score> target = scoreRepository.findBySpotIds(List.of(spots.get(0).getId(), spots.get(2).getId()));

		//then
		Assertions.assertThat(target.size()).isEqualTo(2);
	}

	@Test
	public void 특정관광지의_점수_찾기() throws Exception {
		//given
		List<Spot> spots = spotJpaRepository.saveAll(List.of(givenSpot(), givenSpot(), givenSpot()));
		List<Score> scores = scoreJpaRepository.saveAll(List.of(
				givenScore(spots.get(0).getId()), givenScore(spots.get(1).getId()), givenScore(spots.get(2).getId())
			)
		);

		//when
		Score target = scoreRepository.findBySpotId(spots.get(0).getId());

		//then
		Assertions.assertThat(target).isEqualTo(scores.get(0));
	}

	@Test
	public void location들에_해당하는_관광지의_점수_찾기() throws Exception {
		//given
		List<Spot> spots = spotJpaRepository.saveAll(
			List.of(givenSpotWithLocation(Location.Gujwa_eup), givenSpotWithLocation(Location.Aewol_eup),
				givenSpotWithLocation(Location.Andeok_myeon)));
		List<Score> scores = scoreJpaRepository.saveAll(List.of(
				givenScore(spots.get(0).getId()), givenScore(spots.get(1).getId()), givenScore(spots.get(2).getId())
			)
		);
		List<Location> locations = List.of(Location.Gujwa_eup, Location.Aewol_eup, Location.Andeok_myeon);

		//when
		List<ScoreWithSpotLocationDto> scoreBySpotLocations = scoreRepository.findScoreBySpotLocations(locations);

		//then
		Assertions.assertThat(scoreBySpotLocations.size()).isEqualTo(3);
	}
}
