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
public class WishListsResponseDto {

	private Long memberId;
	private List<WishListResponseDto> wishListResponseDtos;

	public static WishListsResponseDto of(Long memberId, List<WishListResponseDto> wishListResponseDtos) {
		return WishListsResponseDto.builder()
			.memberId(memberId)
			.wishListResponseDtos(wishListResponseDtos)
			.build();
	}

}
