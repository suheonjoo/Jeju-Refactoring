package com.capstone.jejuRefactoring.domain.priority.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.infrastructure.priority.MemberSpotTageWithScoreDto;

public interface MemberSpotTagRepository {
	void saveAll(List<MemberSpotTag> memberSpotTags);

	List<MemberSpotTag> findByMemberIdAndSpotIds(Long memberId, List<Long> spotIds);

	List<MemberSpotTageWithScoreDto> findMemberSpotTageWithScoreAndSpot(Long memberId);
}
