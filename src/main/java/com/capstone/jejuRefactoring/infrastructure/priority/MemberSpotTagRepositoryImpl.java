package com.capstone.jejuRefactoring.infrastructure.priority;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.priority.repository.MemberSpotTagRepository;
import com.capstone.jejuRefactoring.infrastructure.priority.dto.MemberSpotTageWithScoreDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberSpotTagRepositoryImpl implements MemberSpotTagRepository {

	private final MemberSpotTagJpaRepository memberSpotTagJpaRepository;
	private final MemberSpotTagQuerydslRepository memberSpotTagQuerydslRepository;

	@Override
	public void saveAll(List<MemberSpotTag> memberSpotTags) {
		memberSpotTagJpaRepository.saveAllAndFlush(memberSpotTags);
	}

	@Override
	public List<MemberSpotTag> findByMemberIdAndSpotIds(Long memberId, List<Long> spotIds) {
		return memberSpotTagJpaRepository.findByMemberIdAndSpotIds(memberId, spotIds);
	}

	@Override
	public List<MemberSpotTageWithScoreDto> findMemberSpotTageWithScoreAndSpot(Long memberId) {
		return memberSpotTagQuerydslRepository.findMemberSpotTageWithScoreAndSpot(memberId);
	}
}
