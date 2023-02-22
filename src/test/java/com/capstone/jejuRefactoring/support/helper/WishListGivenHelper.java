package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;

public class WishListGivenHelper {
	public static WishList givenWishList(Long memberId) {
		return WishList.builder()
			.member(Member.builder().id(memberId).build())
			.name("위시리스트1")
			.build();
	}
	public static WishListSpotTag givenWishListSpotTag(Long spotId, Long wishListId) {
		return WishListSpotTag.builder()
			.spot(Spot.builder().id(spotId).build())
			.wishList(WishList.builder().id(wishListId).build())
			.build();
	}
}
