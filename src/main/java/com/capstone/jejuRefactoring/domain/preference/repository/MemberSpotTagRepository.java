package com.capstone.jejuRefactoring.domain.preference.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.MemberSpotTageWithScoreDto;

public interface MemberSpotTagRepository {
	void saveAll(List<MemberSpotTag> memberSpotTags);

	List<MemberSpotTag> findByMemberIdAndSpotIds(Long memberId, List<Long> spotIds);

	List<MemberSpotTageWithScoreDto> findMemberSpotTageWithScoreAndSpot(Long memberId);

	boolean isSpotLikExistByMemberIdAndSpotId(Long spotId, Long memberId);

	void deleteSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId);

	void createSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId);
}
