package com.capstone.jejuRefactoring.infrastructure.priority;

import static com.capstone.jejuRefactoring.domain.priority.QScore.*;
import static com.capstone.jejuRefactoring.domain.spot.QSpot.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScoreQuerydslRepository {

	private final JPAQueryFactory query;

	public List<ScoreWithSpotLocationDto> findScoreBySpotLocations(List<Location> locations) {

		return query.select(Projections.constructor(ScoreWithSpotLocationDto.class,
					score.viewScore,
					score.priceScore,
					score.facilityScore,
					score.surroundScore,
					score.spot.location,
					score.spot.id
				)
			)
			.from(score)
			.leftJoin(score.spot, spot)
			.where(spot.location.in(locations))
			.fetch();

	}

}
