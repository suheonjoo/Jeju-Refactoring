package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.spot.Picture;

public interface PictureJpaRepository extends JpaRepository<Picture, Long> {

	@Query(value = "select p from Picture p where p.spot.id in :spotIds")
	List<Picture> findBySpotIds(@Param("spotIds") List<Long> spotIds);

}
