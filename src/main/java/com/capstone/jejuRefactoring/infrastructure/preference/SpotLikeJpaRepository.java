package com.capstone.jejuRefactoring.infrastructure.preference;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;

public interface SpotLikeJpaRepository extends JpaRepository<SpotLikeTag, Long> {

	@Modifying(clearAutomatically = true)
	@Query(value = "update SpotLikeTag slt set slt.likeCount = slt.likeCount + 1 where slt.spot.id = :spotId")
	void increaseLikeCount(@Param("spotId") Long spotId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update SpotLikeTag slt set slt.likeCount = slt.likeCount - 1 where slt.spot.id = :spotId")
	void decreaseLikeCount(@Param("spotId") Long spotId);

	@Query(value = "select slt from SpotLikeTag slt where slt.spot.id = :spotId")
	Optional<SpotLikeTag> findBySpotId(@Param("spotId") Long spotId);
}
