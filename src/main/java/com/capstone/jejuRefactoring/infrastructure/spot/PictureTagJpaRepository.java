package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.spot.PictureTag;

public interface PictureTagJpaRepository extends JpaRepository<PictureTag, Long> {

	@Query(value = "select p from PictureTag p where p.spot.id in :spotIds")
	List<PictureTag> findBySpotIds(@Param("spotIds") List<Long> spotIds);

}
