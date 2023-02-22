package com.capstone.jejuRefactoring.infrastructure.preference;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.preference.repository.SpotLikeTagRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SpotLikeTagRepositoryImpl implements SpotLikeTagRepository {

	private final SpotLikeTagJpaRepository spotLikeTagJpaRepository;

	@Override
	public void increaseLikeCount(Long spotId) {
		spotLikeTagJpaRepository.increaseLikeCount(spotId);
	}

	@Override
	public void decreaseLikeCount(Long spotId) {
		spotLikeTagJpaRepository.decreaseLikeCount(spotId);
	}

	@Override
	public Optional<SpotLikeTag> findBySpotId(Long spotId) {
		return spotLikeTagJpaRepository.findBySpotId(spotId);
	}
}
