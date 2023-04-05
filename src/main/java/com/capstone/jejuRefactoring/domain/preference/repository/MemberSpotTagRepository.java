package com.capstone.jejuRefactoring.domain.preference.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;

public interface MemberSpotTagRepository {
	List<MemberSpotTag> saveAllWithSpringDataJPA(List<MemberSpotTag> memberSpotTags);

	List<MemberSpotTag> findByMemberIdAndSpotIds(Long memberId, List<Long> spotIds);

	boolean isSpotLikExistByMemberIdAndSpotId(Long spotId, Long memberId);

	void deleteSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId);

	void createSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId);

	MemberSpotTag saveAndFlush(MemberSpotTag memberSpotTag);

	void saveAll(List<MemberSpotTag> memberSpotTags);
}
