package com.capstone.jejuRefactoring.infrastructure.spot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.Spot;

public interface SpotJpaRepository extends JpaRepository<Spot, Long> {

	@Query(value = "select s from Spot s join fetch s.pictureTags where s.id in :spotIds")
	List<Spot> findBySpotIdsWithFetchJoin(@Param("spotIds") List<Long> spotIds);

	@Query(value = "select s.id from Spot s")
	List<Long> findAllSpotId();

	@Query(value = "select s.id from Spot s where s.location in :locations")
	List<Long> findBySpotLocations(@Param("locations") List<Location> locations);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE spot SET like = like + 1 WHERE spot_id = :spotId", nativeQuery = true)
	void increaseLikeCount(Long spotId);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE spot SET like = like - 1 WHERE spot_id = :spotId", nativeQuery = true)
	void decreaseLikeCount(Long spotId);
}
