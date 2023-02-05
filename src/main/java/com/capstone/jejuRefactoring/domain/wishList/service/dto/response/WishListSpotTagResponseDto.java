package com.capstone.jejuRefactoring.domain.wishList.service.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishListSpotTagResponseDto {

	private Long wishListId;

	private Long spotId;

	public static WishListSpotTagResponseDto of(Long wishListId, Long spotId) {
		return WishListSpotTagResponseDto.builder()
			.wishListId(wishListId)
			.spotId(spotId)
			.build();
	}

}
