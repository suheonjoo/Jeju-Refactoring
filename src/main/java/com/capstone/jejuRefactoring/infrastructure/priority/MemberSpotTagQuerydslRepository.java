package com.capstone.jejuRefactoring.infrastructure.priority;

import static com.capstone.jejuRefactoring.domain.priority.QMemberSpotTag.*;
import static com.capstone.jejuRefactoring.domain.priority.QScore.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberSpotTagQuerydslRepository {

	private final JPAQueryFactory query;

	public List<MemberSpotTageWithScoreDto> findMemberSpotTageWithScoreAndSpot(Long memberId) {

		return query.select(Projections.constructor(MemberSpotTageWithScoreDto.class,
				score.spot.id,
				memberSpotTag,
				score
			)
		).
			from(memberSpotTag)
			//.leftJoin(spot).on(memberSpotTag.spot.id.eq(spot.id))
			.leftJoin(score).on(score.spot.id.eq(memberSpotTag.spot.id))
			.where(memberSpotTag.member.id.eq(memberId))
			.fetch();



	}
}
