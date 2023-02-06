package com.capstone.jejuRefactoring.infrastructure.spot;

import static com.capstone.jejuRefactoring.domain.priority.QScore.*;
import static com.capstone.jejuRefactoring.domain.spot.QSpot.*;
import static com.capstone.jejuRefactoring.domain.spot.QPictureTag.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.QPictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.querydsl.core.types.OrderSpecifier;
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
		return RepositorySupport.toSlice(content,pageable);
	}

	public List<Spot> findByLocationAndCategory(Location location, Category category) {
		return query.selectFrom(spot)
			.where(spot.location.eq(location))
			.leftJoin(score).on(score.spot.id.eq(spot.id))
			.orderBy(getDoubleOrderSpecifier(category))
			.limit(10)
			.fetch();
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
			.leftJoin(score).on(score.spot.id.eq(spot.id))
			.leftJoin(spot.pictureTags, pictureTag)
			.fetchJoin()
			.where(spot.location.in(locations))
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

	private OrderSpecifier<Double> getDoubleOrderSpecifier(Category category) {
		return switch (category) {
			case VIEW -> spot.score.viewScore.desc();
			case PRICE -> spot.score.priceScore.desc();
			case FACILITY -> spot.score.facilityScore.desc();
			case SURROUND -> spot.score.surroundScore.desc();
			default -> spot.score.rankAverage.asc();
		};
	}

}
