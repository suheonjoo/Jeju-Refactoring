package com.capstone.jejuRefactoring.infrastructure.priority;

import static com.capstone.jejuRefactoring.domain.priority.QMemberSpotTag.*;
import static com.capstone.jejuRefactoring.domain.priority.QScore.*;
import static com.capstone.jejuRefactoring.domain.spot.QSpot.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.infrastructure.priority.dto.MemberSpotTageWithScoreDto;
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
			.leftJoin(spot).on(memberSpotTag.spot.id.eq(spot.id))
			.leftJoin(memberSpotTag.spot.score, score)
			.where(memberSpotTag.member.id.eq(memberId))
			.fetch();



	}
}
