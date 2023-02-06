package com.capstone.jejuRefactoring.domain.wishList.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.common.exception.wishList.SameWishListNameException;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListSpotTagRepository;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListModifyRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSaveRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotIdsResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListSpotTagResponseDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListsResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WishListService {

	private final WishListRepository wishListRepository;
	private final WishListSpotTagRepository wishListSpotTagRepository;

	public void saveWishList(WishListSaveRequestDto wishListSaveRequestDto) {
		validWishListName(wishListSaveRequestDto.getWishListName(), wishListSaveRequestDto.getMemberId());
		wishListRepository.saveWishList(wishListSaveRequestDto.toEntity());
	}

	private void validWishListName(String wishListName, Long memberId) {
		Optional<WishList> findByWishListNameAndMemberId = wishListRepository.findByWishListNameAndMemberId(
			wishListName, memberId);
		if (!findByWishListNameAndMemberId.isPresent()) {
			throw new SameWishListNameException();
		}
	}

	public void changeWishListName(WishListModifyRequestDto wishListModifyRequestDto) {
		validWishListName(wishListModifyRequestDto.getWishListName(), wishListModifyRequestDto.getMemberId());
		wishListRepository.updateWishListNameByWishListIdAndMemberId(wishListModifyRequestDto.getWishListName(),
			wishListModifyRequestDto.getWishListId(), wishListModifyRequestDto.getMemberId());
	}

	public void deleteWishList(WishListDeleteRequestDto wishListDeleteRequestDto) {
		//1. memberId, spotid가 동일한 wishListTag '들'을 삭제한다
		wishListSpotTagRepository.deleteByWishListIdAndSpotId(wishListDeleteRequestDto.getWishListId(),
			wishListDeleteRequestDto.getSpotId());
		//2. memberId, wishListId가 동일한 WishList 를 삭제한다
		wishListRepository.deleteWishListByWishListIdAndMemberId(wishListDeleteRequestDto.getWishListId(),
			wishListDeleteRequestDto.getMemberId());
	}

	public WishListsResponseDto findWishLists(Long memberId) {
		//1. memberId 와 동일한 wishList (id, name 포함) '들'을 불러오면서,
		// wishListTag 들과 패치 조인을 해서 spotId들도 같이 불러온다
		//2. 불러온 데이터를 wishListId 로 groupby, limit(3) 하여 dto로 묶는다
		List<WishList> wishLists = wishListRepository.findByMemberId(memberId);
		return getWishListsResponseDto(memberId, wishLists, getWishListSpotTagesMap(wishLists));
	}

	private WishListsResponseDto getWishListsResponseDto(Long memberId, List<WishList> wishLists,
		Map<Long, List<WishListSpotTagResponseDto>> wishlistSpotTagsMap) {
		List<WishListResponseDto> wishListResponseDtos = new ArrayList<>();
		for (WishList wishList : wishLists) {
			addWishListResponseDtoInWishListResponseDtos(wishlistSpotTagsMap, wishListResponseDtos, wishList);
		}
		return WishListsResponseDto.of(memberId, wishListResponseDtos);
	}

	private void addWishListResponseDtoInWishListResponseDtos(Map<Long, List<WishListSpotTagResponseDto>> wishlistSpotTagsMap,
		List<WishListResponseDto> wishListResponseDtos, WishList wishList) {
		List<Long> spotIds = wishlistSpotTagsMap.get(wishList.getId())
			.stream()
			.map(i -> i.getSpotId())
			.limit(3)
			.collect(Collectors.toList());
		wishListResponseDtos.add(WishListResponseDto.of(wishList, spotIds));
	}

	private Map<Long, List<WishListSpotTagResponseDto>> getWishListSpotTagesMap(List<WishList> wishLists) {
		List<Long> wishListIds = wishLists.stream().map(w -> w.getId()).collect(Collectors.toList());
		List<WishListSpotTag> wishListSpotTags = wishListSpotTagRepository.findByWishListIds(wishListIds);
		Map<Long, List<WishListSpotTagResponseDto>> wishlistSpotTagsMap = wishListSpotTags.stream()
			.map(wt -> WishListSpotTagResponseDto.of(wt.getWishList().getId(), wt.getSpot().getId()))
			.collect(Collectors.groupingBy(w -> w.getWishListId()));
		return wishlistSpotTagsMap;
	}

	public WishListSpotIdsResponseDto findWishListSpotIds(Long wishListId) {
		//1. wishListId 와 동일한 wishListTag 의 spotId '들'을 불러온다
		List<Long> spotIds = wishListSpotTagRepository.findByWishListId(wishListId);
		return WishListSpotIdsResponseDto.of(wishListId, spotIds);
	}

}
