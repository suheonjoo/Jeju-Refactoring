package com.capstone.jejuRefactoring.infrastructure.preference;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.repository.MemberSpotTagRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.MemberSpotTagWithScoreDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberSpotTagRepositoryImpl implements MemberSpotTagRepository {

	private final MemberSpotTagJpaRepository memberSpotTagJpaRepository;
	private final MemberSpotTagQuerydslRepository memberSpotTagQuerydslRepository;
	private final MemberSpotTagJdbcRepository memberSpotTagJdbcRepository;

	@Override
	public List<MemberSpotTag> saveAllWithSpringDataJPA(List<MemberSpotTag> memberSpotTags) {
		return memberSpotTagJpaRepository.saveAllAndFlush(memberSpotTags);
	}

	@Override
	public void saveAll(List<MemberSpotTag> memberSpotTags) {
		memberSpotTagJdbcRepository.saveAll(memberSpotTags);
	}

	@Override
	public List<MemberSpotTag> findByMemberIdAndSpotIds(Long memberId, List<Long> spotIds) {
		return memberSpotTagJpaRepository.findByMemberIdAndSpotIds(memberId, spotIds);
	}


	@Override
	public boolean isSpotLikExistByMemberIdAndSpotId(Long spotId, Long memberId) {
		return memberSpotTagJpaRepository.isSpotLikExistByMemberIdAndSpotId(spotId, memberId);
	}

	@Override
	public void deleteSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId) {
		memberSpotTagJpaRepository.deleteSpotLikeByMemberIdAndSpotId(spotId, memberId);
	}

	@Override
	public void createSpotLikeByMemberIdAndSpotId(Long spotId, Long memberId) {
		memberSpotTagJpaRepository.createSpotLikeByMemberIdAndSpotId(spotId, memberId);
	}

	@Override
	public MemberSpotTag saveAndFlush(MemberSpotTag memberSpotTag) {
		return memberSpotTagJpaRepository.saveAndFlush(memberSpotTag);
	}
}
