package com.capstone.jejuRefactoring.unit.application.wishList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capstone.jejuRefactoring.application.wishList.WishListFacade;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;
import com.capstone.jejuRefactoring.domain.wishList.service.WishListService;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotIdsResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;

@ExtendWith(MockitoExtension.class)
public class WishListFacadeTest {

	@InjectMocks
	WishListFacade wishListFacade;
	@Mock
	WishListService wishListService;
	@Mock
	SpotService spotService;

	@Test
	public void 위시리스트_만들기() throws Exception{
	    //given
		// wishListService.saveWishList(wishListSaveRequestDto);

	    //when

	    //then
	}

	@Test
	public void 위시리스트_삭제하기() throws Exception{
	    //given
		// wishListService.deleteWishList(wishListDeleteRequestDto);

	    //when

	    //then
	}

	@Test
	public void 특정위시리스트_안에_관광지_한개_삭제하기() throws Exception{
	    //given
		// wishListService.deleteWishListSpotTagInWishList(wishListSpotTagDeleteRequestDto);

	    //when

	    //then
	}

	@Test
	public void 위시리스트이름_변경하기() throws Exception{
	    //given
		// wishListService.changeWishListName(wishListModifyRequestDto);

	    //when

	    //then
	}

	@Test
	public void 위시리스트메인페이지에서_관광지사진과_함께_위시리스트_사진_보여주기() throws Exception{
	    //given
		// WishListsResponseDto wishListsResponseDto = wishListService.findWishLists(memberId);
		// return spotService.getPictureTagsForWishLists(wishListsResponseDto);

	    //when

	    //then
	}

	@Test
	public void 특정_위시리스트_정보_보여주기() throws Exception{
	    //given
		// WishListSpotIdsResponseDto wishListSpotIds = wishListService.findWishListSpotIds(wishListId);
		// return spotService.getSpotInfoBySpotIdsForRoute(wishListSpotIds);

	    //when

	    //then
	}

}
























