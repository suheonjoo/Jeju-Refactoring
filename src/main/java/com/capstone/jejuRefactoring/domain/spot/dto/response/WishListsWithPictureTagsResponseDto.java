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
public class WishListsWithPictureTagsResponseDto {

	private Long memberId;

	private List<SpotPictureTagForWishListResponseDto> wishListResponseDtos;

	public static WishListsWithPictureTagsResponseDto of(Long memberId, List<SpotPictureTagForWishListResponseDto> wishListResponseDtos) {
		return WishListsWithPictureTagsResponseDto.builder()
			.memberId(memberId)
			.wishListResponseDtos(wishListResponseDtos)
			.build();
	}


}
