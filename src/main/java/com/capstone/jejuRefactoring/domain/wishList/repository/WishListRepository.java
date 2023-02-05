package com.capstone.jejuRefactoring.domain.wishList.repository;

import java.util.List;
import java.util.Optional;

import com.capstone.jejuRefactoring.domain.wishList.WishList;

public interface WishListRepository {

	void saveWishList(WishList wishList);

	void updateWishListNameByWishListIdAndMemberId(String wishListName, Long wishListId, Long memberId);

	Optional<WishList> findByWishListNameAndMemberId(String wishListName, Long memberId);

	void deleteWishListByWishListIdAndMemberId(Long wishListId, Long memberId);

	List<WishList> findByMemberIdWithWishListSpotTags(Long memberId);

	List<WishList> findByMemberId(Long memberId);

}
