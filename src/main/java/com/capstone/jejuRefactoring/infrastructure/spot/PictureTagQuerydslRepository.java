package com.capstone.jejuRefactoring.infrastructure.spot;

import static com.capstone.jejuRefactoring.domain.spot.QPictureTag.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PictureTagQuerydslRepository {

	private final JPAQueryFactory query;

	public List<PictureTagUrlDto> findNPictureTagForSpotIds(List<Long> spoIds, Integer limit) {

		return query.select(Projections.constructor(PictureTagUrlDto.class,
				pictureTag.spot.id,
				pictureTag.url,
				pictureTag.id.min())
			)
			.from(pictureTag)
			.where(pictureTag.spot.id.in(spoIds))
			.groupBy(pictureTag.spot.id, pictureTag.url)
			.limit(limit)
			.fetch();

	}

}
