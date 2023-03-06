package com.capstone.jejuRefactoring.support.helper;

import java.util.List;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.preference.dto.request.PriorityWeightDto;
import com.capstone.jejuRefactoring.domain.preference.dto.response.LikeFlipResponse;
import com.capstone.jejuRefactoring.domain.preference.dto.response.SpotIdsWithPageInfoDto;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.ScoreResponse;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.ScoreWithSpotLocationDto;

public class PreferenceGivenHelper {

	public static Score givenScore(Long spotId) {
		return Score.builder()
			.spot(Spot.builder().id(spotId).build())
			.facilityScore(20d)
			.priceScore(10d)
			.surroundScore(30d)
			.viewScore(40d)
			.facilityRank(1d)
			.priceRank(2d)
			.surroundRank(3d)
			.viewRank(4d)
			.build();
	}

	public static Score givenScoreWithSameScore(Long spotId) {
		return Score.builder()
			.spot(Spot.builder().id(spotId).build())
			.facilityScore(10d)
			.priceScore(10d)
			.surroundScore(10d)
			.viewScore(10d)
			.facilityRank(1d)
			.priceRank(1d)
			.surroundRank(1d)
			.viewRank(1d)
			.build();
	}

	public static PriorityWeightDto givenPriorityWeightDto(Integer viewWeight, Integer priceWeight,
		Integer facilityWeight, Integer surroundWeight) {
		return PriorityWeightDto.builder()
			.facilityWeight(facilityWeight)
			.surroundWeight(surroundWeight)
			.priceWeight(priceWeight)
			.viewWeight(viewWeight)
			.isSameWeight(false)
			.build();
	}

	public static ScoreWithSpotLocationDto givenScoreWithSpotLocationDto(Long spotId, Double score) {
		return ScoreWithSpotLocationDto.builder()
			.spotId(spotId)
			.location(Location.Aewol_eup)
			.facilityScore(0d)
			.priceScore(0d)
			.surroundScore(score)
			.viewScore(0d)
			.build();
	}

	public static MemberSpotTag givenMemberSpotTag(Long spotId, Long memberId) {
		return MemberSpotTag.builder()
			.spot(Spot.builder().id(spotId).build())
			.member(Member.builder().id(memberId).build())
			.IsLikeExist(false)
			.personalScore(4d)
			.build();
	}

	public static MemberSpotTag givenMemberSpotTagWithPersonalScore(Long spotId, Long memberId, Double personalScore) {
		return MemberSpotTag.builder()
			.spot(Spot.builder().id(spotId).build())
			.member(Member.builder().id(memberId).build())
			.IsLikeExist(false)
			.personalScore(personalScore)
			.build();
	}

	public static SpotLikeTag givenSpotLikeTag(Long spotId) {
		return SpotLikeTag.builder()
			.likeCount(0)
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

	public static MemberSpotTag givenMemberSpotTagWithId(Long memberSpotTagId, Long spotId, Long memberId) {
		return MemberSpotTag.builder()
			.id(memberSpotTagId)
			.spot(Spot.builder().id(spotId).build())
			.member(Member.builder().id(memberId).build())
			.IsLikeExist(false)
			.personalScore(4d)
			.build();
	}

	public static MemberSpotTag givenMemberSpotTagWithSpotLike(Long spotId, Long memberId, boolean isLikeExist) {
		return MemberSpotTag.builder()
			.spot(Spot.builder().id(spotId).build())
			.member(Member.builder().id(memberId).build())
			.personalScore(4d)
			.IsLikeExist(isLikeExist)
			.build();
	}

	public static SpotLikeTag givenSpotLikeTag(Long spotId, int count) {
		return SpotLikeTag.builder()
			.likeCount(count)
			.spot(Spot.builder().id(spotId).build())
			.build();
	}

	public static SpotIdsWithPageInfoDto givenSpotIdsWithPageInfoDto(List<Long> spotIds, boolean hasNext) {
		return SpotIdsWithPageInfoDto.builder()
			.spotIds(spotIds)
			.hasNext(hasNext)
			.build();
	}

	public static PriorityWeightDto givenPriorityWeightDto() {
		return PriorityWeightDto.builder()
			.isSameWeight(true)
			.build();
	}

	public static LikeFlipResponse givenLikeFlipResponse(boolean like, int likeCount) {
		return LikeFlipResponse.builder()
			.like(like)
			.likeCount(likeCount)
			.build();
	}

	public static ScoreResponse givenScoreResponse(Score score) {
		return ScoreResponse.from(score);
	}
}
