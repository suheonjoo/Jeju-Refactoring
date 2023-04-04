package com.capstone.jejuRefactoring.domain.preference.repository;

import java.util.Optional;

import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;

import jakarta.persistence.criteria.CriteriaBuilder;

public interface SpotLikeTagRepository {

	void increaseLikeCount(Long spotId);

	void decreaseLikeCount(Long spotId);

	Optional<SpotLikeTag> findBySpotId(Long spotId);

	SpotLikeTag saveAndFlush(SpotLikeTag spotLikeTag);
}
