package com.capstone.jejuRefactoring.domain.preference.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.MemberSpotTagWithScoreDto;

public interface MemberSpotTagRepository {
	List<MemberSpotTag> saveAll(List<MemberSpotTag> memberSpotTags);

	List<MemberSpotTag> findByMemberIdAndSpotIds(Long memberId, List<Long> spotIds);

	boolean isSpotLikExistByMemberIdAndSpotId(Long spotId, Long memberId);

	void deleteSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId);

	void createSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId);
}
