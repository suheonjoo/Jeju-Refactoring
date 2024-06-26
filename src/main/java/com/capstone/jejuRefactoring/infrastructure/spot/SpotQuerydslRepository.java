package com.capstone.jejuRefactoring.infrastructure.spot;

import static com.capstone.jejuRefactoring.domain.preference.QScore.*;
import static com.capstone.jejuRefactoring.domain.spot.QSpot.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.capstone.jejuRefactoring.common.support.RepositorySupport;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.SpotWithCategoryScoreDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SpotQuerydslRepository {

	private final JPAQueryFactory query;

	public List<Spot> findByLocationsAndSpotIds(List<Long> spotIds, List<Location> locations) {
		return query.selectFrom(spot)
			.where(spot.location.in(locations), spot.id.in(spotIds))
			.fetch();
	}

	public List<SpotWithCategoryScoreDto> findWithCategoryScoreByLocation(List<Location> locations, Category category) {
		return query.select(Projections.constructor(SpotWithCategoryScoreDto.class,
					spot.id,
					spot.name,
					spot.address,
					spot.description,
					spot.location,
					// score.surroundScore
					getCategoryScore(category)
				)
			)
			.from(spot)
			.leftJoin(score).on(spot.id.eq(score.spot.id))
			.where(spot.location.in(locations))
			.fetch();

	}


	private NumberPath<Double> getCategoryScore(Category category) {
		return switch (category) {
			case VIEW -> score.viewScore;
			case PRICE -> score.priceScore;
			case FACILITY -> score.facilityScore;
			case SURROUND -> score.surroundScore;
			default -> score.rankAverage;
		};
	}

	// 쿼리 최적화 커버링 인덱스로 빠르게 땡겨오고 필요한 필드만 dto로 조회함과 동시에 이미 알고 있는 필드는 as 표현식
	// 쿼리최적화 no offset
	public Slice<SpotPageResponse> findPageBySpotName(String spotName, Long lastSpotId, Pageable pageable) {

		List<Long> ids = query
			.select(spot.id)
			.from(spot)
			.where(spot.name.like(spotName + "%"),
				gtSpotId(lastSpotId))
			.orderBy(spot.id.asc())
			.limit(pageable.getPageSize() + 1) //// limit보다 데이터를 1개 더 들고와서, 해당 데이터가 있다면 hasNext 변수에 true 를 넣어 알린다
			.fetch();

		// 1-1) 대상이 없을 경우 추가 쿼리 수행 할 필요 없이 바로 반환
		if (CollectionUtils.isEmpty(ids)) {
			return new SliceImpl<>(new ArrayList<>(), pageable, false);
		}

		// 2)
		List<SpotPageResponse> content = query
			.select(Projections.constructor(SpotPageResponse.class,
				spot.id.as("spotId"),
				spot.name,
				spot.address,
				spot.description,
				spot.location))
			.from(spot)
			.where(spot.id.in(ids),
				gtSpotId(lastSpotId))
			.orderBy(spot.id.asc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		return RepositorySupport.toSlice(content, pageable);
	}

	private BooleanExpression gtSpotId(Long lastSpotId) {
		if (lastSpotId == null)
			return null;
		return spot.id.gt(lastSpotId);
	}


	public Slice<SpotPageResponse> findPageOldBySpotName(String spotName, Pageable pageable) {

		List<SpotPageResponse> content = query
			.select(Projections.constructor(SpotPageResponse.class,
				spot.id.as("spotId"),
				spot.name,
				spot.address,
				spot.description,
				spot.location))
			.from(spot)
			.where(spot.name.like(spotName + "%"))
			.orderBy(spot.id.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return RepositorySupport.toSlice(content, pageable);
	}


}
