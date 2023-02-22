package com.capstone.jejuRefactoring.infrastructure.wishList;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;

public interface WishListSpotTagJpaRepository extends JpaRepository<WishListSpotTag, Long> {

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from WishListSpotTag wt where wt.wishList.id = :wishListId and wt.spot.id = :spotId ")
	void deleteByWishListIdAndSpotId(@Param("wishListId") Long wishListId, @Param("spotId") Long spotId);

	@Query(value = "select wt from WishListSpotTag wt where wt.wishList.id IN :wishListIds")
	List<WishListSpotTag> findByWishListIds(@Param("wishListIds") List<Long> wishListIds);

	@Query(value = "select wt.spot.id from WishListSpotTag wt where wt.wishList.id = :wishListId")
	List<Long> findSpotIdsByWishListId(@Param("wishListId") Long wishListId);
}
