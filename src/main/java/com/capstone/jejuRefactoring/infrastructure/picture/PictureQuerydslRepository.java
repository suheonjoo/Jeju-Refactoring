package com.capstone.jejuRefactoring.infrastructure.picture;

import static com.capstone.jejuRefactoring.common.support.RepositorySupport.*;
import static com.capstone.jejuRefactoring.domain.picture.QPicture.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PictureQuerydslRepository {

	private final JPAQueryFactory query;

	public Slice<Picture> findPageBySpotId(Long spotId, Pageable pageable) {
		List<Picture> pictures = query.selectFrom(picture)
			.where(picture.spot.id.eq(spotId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();
		return toSlice(pictures, pageable);
	}

	public Slice<Picture> findOpPageBySpotId(Long spotId, Long lastPictureId, Pageable pageable) {

		List<Picture> pictures = query
			.selectFrom(picture)
			.where(
				ltPictureId(lastPictureId),
				picture.spot.id.eq(spotId)
			)
			.orderBy(picture.id.asc())
			.limit(pageable.getPageSize()+1)
			.fetch();

		return toSlice(pictures, pageable);

	}

	private BooleanExpression ltPictureId(Long pictureId) {
		if (pictureId == null) {
			return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
		}
		return picture.id.gt(pictureId);
	}



}
