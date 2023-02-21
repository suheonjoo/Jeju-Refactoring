package com.capstone.jejuRefactoring.infrastructure.preference;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.preference.Score;

public interface ScoreJpaRepository extends JpaRepository<Score, Long> {

	@Query(value = "select s from Score s where s.spot.id in :spotIds")
	List<Score> findBySpotIds(@Param("spotIds") List<Long> spotIds);

	@Query(value = "select s from Score s where s.spot.id = :spotId")
	Score findBySpotId(@Param("spotId") Long spotId);
}
