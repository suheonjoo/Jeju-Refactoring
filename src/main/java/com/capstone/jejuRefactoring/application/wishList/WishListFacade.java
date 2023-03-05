package com.capstone.jejuRefactoring.application.wishList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotsForRouteDto;
import com.capstone.jejuRefactoring.domain.spot.dto.response.WishListsWithPictureTagsResponseDto;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;
import com.capstone.jejuRefactoring.domain.wishList.service.WishListService;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListModifyRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSaveRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSpotTagDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotIdsResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListFacade {

	private final WishListService wishListService;
	private final SpotService spotService;

	@Transactional
	public void saveWishList(WishListSaveRequestDto wishListSaveRequestDto) {
		wishListService.saveWishList(wishListSaveRequestDto);
	}

	@Transactional
	public void deleteWishList(WishListDeleteRequestDto wishListDeleteRequestDto) {
		wishListService.deleteWishList(wishListDeleteRequestDto);
	}

	@Transactional
	public void deleteWishListSpotTagInWishList(WishListSpotTagDeleteRequestDto wishListSpotTagDeleteRequestDto) {
		wishListService.deleteWishListSpotTagInWishList(wishListSpotTagDeleteRequestDto);
	}

	@Transactional
	public void reviseWishListName(WishListModifyRequestDto wishListModifyRequestDto) {
		wishListService.changeWishListName(wishListModifyRequestDto);
	}

	public WishListsWithPictureTagsResponseDto showWishLists(Long memberId) {
		//1. 위시리스트 메인 페이지에 보여줄, wishListId, wishListName, wishList 안에 사진들 리스트 가져오기
		WishListsResponseDto wishListsResponseDto = wishListService.findWishLists(memberId);
		return spotService.getPictureTagsForWishLists(wishListsResponseDto);
	}

	public SpotsForRouteDto showWishList(Long memberId, Long wishListId) {
		//1. 특정 위시리스트에 있는 spotId '들' 찾아오기
		WishListSpotIdsResponseDto wishListSpotIds = wishListService.findWishListSpotIds(wishListId);
		//2. 찾아온 spotId '들' 바탕으로 spotService 한테 주기
		return spotService.getSpotInfoBySpotIdsForRoute(wishListSpotIds);
	}

}
