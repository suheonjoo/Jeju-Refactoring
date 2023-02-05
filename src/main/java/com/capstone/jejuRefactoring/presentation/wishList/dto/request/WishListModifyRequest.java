package com.capstone.jejuRefactoring.presentation.wishList.dto.request;

import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListModifyRequestDto;

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
public class WishListModifyRequest {

	private Long wishListId;
	private Long memberId;
	private String wishListName;

	public WishListModifyRequestDto toWishListModifyRequestDto(Long memberId, Long wishListId) {
		return WishListModifyRequestDto.builder()
			.wishListId(wishListId)
			.wishListName(wishListName)
			.memberId(memberId)
			.build();
	}

}
