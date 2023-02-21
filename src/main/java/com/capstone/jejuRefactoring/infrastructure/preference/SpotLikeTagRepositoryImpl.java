package com.capstone.jejuRefactoring.infrastructure.preference;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.preference.repository.SpotLikeTagRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SpotLikeTagRepositoryImpl implements SpotLikeTagRepository {

	private final SpotLikeJpaRepository spotLikeJpaRepository;

	@Override
	public void increaseLikeCount(Long spotId) {
		spotLikeJpaRepository.increaseLikeCount(spotId);
	}

	@Override
	public void decreaseLikeCount(Long spotId) {
		spotLikeJpaRepository.decreaseLikeCount(spotId);
	}

	@Override
	public Optional<SpotLikeTag> findBySpotId(Long spotId) {
		return spotLikeJpaRepository.findBySpotId(spotId);
	}
}
