package com.capstone.jejuRefactoring.support.helper;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;

public class WishListGivenHelper {
	public static WishList givenWishList(Member member) {
		return WishList.builder()
			.name("위시리스트1")
			.member(member)
			.build();
	}
	public static WishListSpotTag givenWishListSpotTag(Spot spot, WishList wishList) {
		return WishListSpotTag.builder()
			.spot(spot)
			.wishList(wishList)
			.build();
	}
}
