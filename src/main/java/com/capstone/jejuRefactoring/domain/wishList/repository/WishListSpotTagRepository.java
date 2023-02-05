package com.capstone.jejuRefactoring.domain.wishList.repository;

import java.util.List;

import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;

public interface WishListSpotTagRepository {

	void deleteByWishListIdAndSpotId(Long wishListId, Long spotId);

	List<WishListSpotTag> findByWishListIds(List<Long> wishListIds);

	List<Long> findByWishListId(Long wishListId);

}
