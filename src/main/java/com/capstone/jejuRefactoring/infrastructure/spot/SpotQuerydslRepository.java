package com.capstone.jejuRefactoring.infrastructure.spot;

import static com.capstone.jejuRefactoring.domain.priority.QScore.*;
import static com.capstone.jejuRefactoring.domain.spot.QPictureTag.*;
import static com.capstone.jejuRefactoring.domain.spot.QSpot.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SpotQuerydslRepository {

	private final JPAQueryFactory query;

	public Slice<Spot> findPageBySpotIds(List<Long> spotIds, List<Location> locations, Pageable pageable) {
		List<Spot> content = query.selectFrom(spot)
			.where(spot.id.in(spotIds).and(spot.location.in(locations)))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return RepositorySupport.toSlice(content, pageable);
	}

	public List<SpotWithCategoryScoreDto> findWithCategoryScoreByLocation(List<Location> locations, Category category) {
		return query.select(Projections.constructor(SpotWithCategoryScoreDto.class,
					spot.id,
					spot.name,
					spot.address,
					spot.description,
					spot.location,
					getFacilityRank(category)
				)
			)
			.from(spot)
			.leftJoin(spot.score,score)
			.where(spot.location.in(locations))
			.fetch();

	}

	public List<TestDto> test(Category category) {
		return query.select(Projections.constructor(TestDto.class,
					spot.id,
					getFacilityRank(category)
				)
			)
			.from(spot)
			.leftJoin(spot.score,score)
			.fetch();

	}

	private NumberPath<Double> getFacilityRank(Category category) {
		return switch (category) {
			case VIEW -> score.viewScore;
			case PRICE -> score.priceScore;
			case FACILITY -> score.facilityScore;
			case SURROUND -> score.surroundScore;
			default -> score.rankAverage;
		};
	}

}
