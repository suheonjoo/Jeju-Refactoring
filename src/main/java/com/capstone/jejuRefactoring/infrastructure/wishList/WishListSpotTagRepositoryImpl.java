package com.capstone.jejuRefactoring.infrastructure.wishList;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WishListSpotTagRepositoryImpl implements WishListSpotTagRepository {

	private final WishListSpotTagJpaRepository wishListSpotTagJpaRepository;

	public void deleteByWishListIdAndSpotId(Long wishListId, Long spotId) {
		wishListSpotTagJpaRepository.deleteByWishListIdAndSpotId(wishListId, spotId);
	}

	public List<WishListSpotTag> findByWishListIds(List<Long> wishListIds) {
		return wishListSpotTagJpaRepository.findByWishListIds(wishListIds);
	}

	public List<Long> findSpotIdsByWishListId(Long wishListId) {
		return wishListSpotTagJpaRepository.findSpotIdsByWishListId(wishListId);
	}

}
