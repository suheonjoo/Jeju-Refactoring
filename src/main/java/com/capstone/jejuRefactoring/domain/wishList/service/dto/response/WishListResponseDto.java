package com.capstone.jejuRefactoring.domain.wishList.service.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.wishList.WishList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishListResponseDto {

	private Long wishListId;
	private String wishListName;
	private List<Long> spotIds;

	public static WishListResponseDto of(WishList wishList, List<Long> spotIds) {
		return WishListResponseDto.builder()
			.wishListId(wishList.getId())
			.wishListName(wishList.getName())
			.spotIds(spotIds)
			.build();
	}

}
