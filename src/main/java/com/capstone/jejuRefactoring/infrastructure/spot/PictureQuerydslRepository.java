package com.capstone.jejuRefactoring.infrastructure.spot;

import static com.capstone.jejuRefactoring.common.support.RepositorySupport.*;
import static com.capstone.jejuRefactoring.domain.spot.QPicture.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.spot.Picture;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class PictureQuerydslRepository {

	private final JPAQueryFactory query;

	public List<PictureUrlDto> findNPictureForSpotIds(List<Long> spoIds, Integer limit) {

		return query.select(Projections.constructor(PictureUrlDto.class,
				picture.spot.id,
				picture.url,
				picture.id.min())
			)
			.from(picture)
			.where(picture.spot.id.in(spoIds))
			.groupBy(picture.spot.id, picture.url)
			.limit(limit)
			.fetch();

	}

	public Slice<Picture> findPageBySpotId(@Param("spotId") Long spotId, Pageable pageable) {
		List<Picture> pictures = query.selectFrom(picture)
			.where(picture.spot.id.eq(spotId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return toSlice(pictures, pageable);
	}

}
