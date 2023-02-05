package com.capstone.jejuRefactoring.presentation.wishList.dto.request;

import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListDeleteRequestDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishListDeleteRequest {

	private Long wishListId ;
	private Long spotId;
	private Long memberId;

	public WishListDeleteRequestDto toWishListDeleteRequestDto(Long memberId) {
		return WishListDeleteRequestDto.builder()
			.wishListId(wishListId)
			.memberId(memberId)
			.spotId(spotId)
			.build();
	}

}
