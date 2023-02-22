package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;

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

	public static MemberSpotTag givenMemberSpotTag(Long spotId, Long memberId) {
		return MemberSpotTag.builder()
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
}
