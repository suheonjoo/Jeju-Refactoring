package com.capstone.jejuRefactoring.domain.spot.dto.response;

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
public class WishListsWithPicturesResponseDto {

	private Long memberId;

	private List<SpotPictureForWishListResponseDto> wishListResponseDtos;

	public static WishListsWithPicturesResponseDto of(Long memberId, List<SpotPictureForWishListResponseDto> wishListResponseDtos) {
		return WishListsWithPicturesResponseDto.builder()
			.memberId(memberId)
			.wishListResponseDtos(wishListResponseDtos)
			.build();
	}


}
