package com.capstone.jejuRefactoring.support.helper;

import java.util.ArrayList;
import java.util.List;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListModifyRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSaveRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSpotTagDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotIdsResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListModifyRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListSaveRequest;

public class WishListGivenHelper {
	public static WishList givenWishList(Long memberId) {
		return WishList.builder()
			.member(Member.builder().id(memberId).build())
			.name("위시리스트1")
			.build();
	}


	public static WishList givenWishListWithWishListName(Long memberId, String wishListName) {
		return WishList.builder()
			.member(Member.builder().id(memberId).build())
			.name(wishListName)
			.build();
	}

	public static WishList givenWishListWithId(Long memberId, Long wishListId) {
		return WishList.builder()
			.id(wishListId)
			.member(Member.builder().id(memberId).build())
			.name("위시리스트1")
			.build();
	}

	public static WishListModifyRequestDto givenWishListModifyRequestDto() {
		return WishListModifyRequestDto.builder()
			.wishListId(1l)
			.wishListName("위시리스트1")
			.memberId(1l)
			.build();
	}

	public static WishListDeleteRequestDto givenWishListDeleteRequestDto() {
		return WishListDeleteRequestDto.builder()
			.wishListId(1l)
			.memberId(1l)
			.build();
	}

	public static WishListSpotTagDeleteRequestDto givenWishListSpotTagDeleteRequestDto() {
		return WishListSpotTagDeleteRequestDto.builder()
			.spotId(1l)
			.wishListId(1l)
			.memberId(1l)
			.build();
	}


	public static WishList givenWishListFetchJoinWithWishSpotTags(Long wishListId, Long memberId) {
		List<WishListSpotTag> wishListSpotTags = List.of(givenWishListSpotTag(1l, wishListId), givenWishListSpotTag(2l, wishListId));

		return WishList.builder()
			.id(wishListId)
			.name("위시리스트1")
			.member(Member.builder().id(memberId).build())
			.wishListSpotTages(wishListSpotTags)
			.build();
	}

	public static WishListSpotTag givenWishListSpotTag(Long spotId, Long wishListId) {
		return WishListSpotTag.builder()
			.spot(Spot.builder().id(spotId).build())
			.wishList(WishList.builder().id(wishListId).build())
			.build();
	}

	public static WishListsResponseDto givenWishListsResponseDto() {
		List<WishListResponseDto> listResponseDtos = List.of(
			givenWishListResponseDto(List.of(1l, 2l, 3l), "위시리스트1"),
			givenWishListResponseDto(List.of(1l, 2l, 3l), "위시리스트2"),
			givenWishListResponseDto(List.of(1l, 2l, 3l), "위시리스트3")
		);

		return WishListsResponseDto.builder()
			.memberId(1l)
			.wishListResponseDtos(listResponseDtos)
			.build();
	}

	public static WishListSpotIdsResponseDto givenWishListSpotIdsResponseDto(List<Long> spotIds) {
		return WishListSpotIdsResponseDto.builder()
			.spotIds(spotIds)
			.wishListId(1l)
			.build();
	}

	private static WishListResponseDto givenWishListResponseDto(List<Long> spotIds, String wishListName) {
		return WishListResponseDto.builder()
			.spotIds(spotIds)
			.wishListName(wishListName)
			.build();
	}

	public static WishListSaveRequestDto givenWishListSaveRequestDto(Long memberId, String wishListName) {
		return WishListSaveRequestDto.builder()
			.memberId(memberId)
			.wishListName(wishListName)
			.build();
	}

	public static WishListSaveRequest givenWishListSaveRequest(String wishListName, Long memberId) {
		return WishListSaveRequest.builder()
			.wishListName(wishListName)
			.memberId(memberId)
			.build();
	}

	public static WishListModifyRequest givenWishListModifyRequest(Long wishListId, String wishListName, Long memberId) {
		return WishListModifyRequest.builder()
			.wishListId(wishListId)
			.wishListName(wishListName)
			.memberId(memberId)
			.build();
	}
}
