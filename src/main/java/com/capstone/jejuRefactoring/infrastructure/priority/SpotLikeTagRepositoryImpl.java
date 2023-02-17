package com.capstone.jejuRefactoring.infrastructure.priority;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.priority.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.priority.repository.SpotLikeTagRepository;

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
