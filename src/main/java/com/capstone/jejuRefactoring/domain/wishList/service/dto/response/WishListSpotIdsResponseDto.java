package com.capstone.jejuRefactoring.domain.wishList.service.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishListSpotIdsResponseDto {

	private Long wishListId;
	private List<Long> spotIds;

	public static WishListSpotIdsResponseDto of(Long wishListId, List<Long> spotIds) {
		return WishListSpotIdsResponseDto.builder()
			.wishListId(wishListId)
			.spotIds(spotIds)
			.build();
	}

}
