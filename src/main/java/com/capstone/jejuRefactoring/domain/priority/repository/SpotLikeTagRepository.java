package com.capstone.jejuRefactoring.domain.priority.repository;

import java.util.Optional;

import com.capstone.jejuRefactoring.domain.priority.SpotLikeTag;

public interface SpotLikeTagRepository {

	void increaseLikeCount(Long spotId);

	void decreaseLikeCount(Long spotId);

	Optional<SpotLikeTag> findBySpotId(Long spotId);
}
