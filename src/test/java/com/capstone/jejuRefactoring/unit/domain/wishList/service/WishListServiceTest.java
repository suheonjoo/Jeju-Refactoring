package com.capstone.jejuRefactoring.unit.domain.wishList.service;

import static com.capstone.jejuRefactoring.support.helper.WishListGivenHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capstone.jejuRefactoring.common.exception.wishList.SameWishListNameException;
import com.capstone.jejuRefactoring.domain.spot.repository.PictureTagRepository;
import com.capstone.jejuRefactoring.domain.spot.repository.SpotRepository;
import com.capstone.jejuRefactoring.domain.spot.service.SpotService;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.domain.wishList.service.WishListService;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSaveRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSpotTagDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;

@ExtendWith(MockitoExtension.class)
public class WishListServiceTest {

	@InjectMocks
	WishListService wishListService;
	@Mock
	WishListRepository wishListRepository;
	@Mock
	WishListSpotTagRepository wishListSpotTagRepository;

	@Test
	public void 위시리스트_만들기() throws Exception {
		//given
		WishListSaveRequestDto wishListSaveRequestDto = givenWishListSaveRequestDto(1l, "새로운 위시리스트");
		WishList givenWishList = givenWishListWithId(1l, 1l);
		given(wishListRepository.findByWishListNameAndMemberId(any(), any()))
			.willReturn(Optional.of(givenWishList));
		given(wishListRepository.saveWishList(any()))
			.willReturn(givenWishList);

		//when
		WishList result = wishListService.saveWishList(wishListSaveRequestDto);

		// when, then
		assertAll(
			() -> assertThat(result).isEqualTo(givenWishList),
			() -> verify(wishListRepository).saveWishList(any(WishList.class))
		);
	}

	@Test
	public void 위시리스트_만들기는데_중복된_이름이_있으면_예외를_반환한다() throws Exception {
		//given
		WishListSaveRequestDto wishListSaveRequestDto = givenWishListSaveRequestDto(1l, "새로운 위시리스트");
		given(wishListRepository.findByWishListNameAndMemberId(any(), any()))
			.willReturn(Optional.empty());

		// when, then
		assertAll(
			() -> assertThatThrownBy(() -> wishListService.saveWishList(wishListSaveRequestDto))
				.isExactlyInstanceOf(SameWishListNameException.class),
			() -> verify(wishListRepository).findByWishListNameAndMemberId(wishListSaveRequestDto.getWishListName(), 1l)
		);
	}

	@Test
	public void 위시리시트_이름_바꾸기_그러나_중복이름이_있으면_예외를_반환한다() throws Exception {
		//given
		WishList givenWishList = givenWishListWithId(1l, 1l);
		given(wishListRepository.findByWishListNameAndMemberId(any(), any()))
			.willReturn(Optional.empty());

		//when, then
		assertAll(
			() -> assertThatThrownBy(() -> wishListService.changeWishListName(givenWishListModifyRequestDto()))
				.isExactlyInstanceOf(SameWishListNameException.class)
		);
	}

	@Test
	public void 위시리스트_안에_특정관광지_삭제하기() throws Exception {
		// given
		WishListSpotTagDeleteRequestDto wishListSpotTagDeleteRequestDto = givenWishListSpotTagDeleteRequestDto();

		// when
		wishListService.deleteWishListSpotTagInWishList(wishListSpotTagDeleteRequestDto);

		// then
		assertAll(
			() -> verify(wishListSpotTagRepository).deleteByWishListIdAndSpotId(
				wishListSpotTagDeleteRequestDto.getWishListId(), wishListSpotTagDeleteRequestDto.getSpotId()),
			() -> verify(wishListRepository).deleteWishListByWishListIdAndMemberId(
				wishListSpotTagDeleteRequestDto.getWishListId(), wishListSpotTagDeleteRequestDto.getMemberId())
		);
	}

	@Test
	public void 위시리시트_삭제하기() throws Exception {
		//given
		WishListDeleteRequestDto wishListDeleteRequestDto = givenWishListDeleteRequestDto();
		given(wishListSpotTagRepository.findSpotIdsByWishListId(any()))
			.willReturn(List.of(1l, 2l, 3l));

		//when
		wishListService.deleteWishList(wishListDeleteRequestDto);
		//then
		assertAll(
			() -> verify(wishListSpotTagRepository).deleteByWishListIdAndSpotIds(
				wishListDeleteRequestDto.getWishListId(), List.of(1l, 2l, 3l)),
			() -> verify(wishListRepository).deleteWishListByWishListIdAndMemberId(
				wishListDeleteRequestDto.getWishListId(), wishListDeleteRequestDto.getMemberId())
		);
	}

	@Test
	public void 사용자의_위시리스트_정보_가져오기() throws Exception {
		//given
		List<WishList> wishLists = List.of(givenWishListWithId(1l, 1l), givenWishListWithId(1l, 2l));
		List<WishListSpotTag> wishListSpotTags = List.of(givenWishListSpotTag(1l, 1l), givenWishListSpotTag(2l, 1l),
			givenWishListSpotTag(3l, 1l),
			givenWishListSpotTag(1l, 2l));
		given(wishListRepository.findByMemberId(any()))
			.willReturn(wishLists);
		given(wishListSpotTagRepository.findByWishListIds(anyList()))
			.willReturn(wishListSpotTags);

		//when
		WishListsResponseDto result = wishListService.findWishLists(1l);

		//then
		assertThat(result.getMemberId()).isEqualTo(1l);
		assertThat(result.getWishListResponseDtos().size()).isEqualTo(2);
		assertThat(result.getWishListResponseDtos().get(0).getSpotIds().size()).isEqualTo(3);
	}

}
