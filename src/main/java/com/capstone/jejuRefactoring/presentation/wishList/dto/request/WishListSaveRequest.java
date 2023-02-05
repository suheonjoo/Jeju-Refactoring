package com.capstone.jejuRefactoring.presentation.wishList.dto.request;

import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSaveRequestDto;

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
public class WishListSaveRequest {

	private String wishListName;

	private Long memberId;

	public WishListSaveRequestDto toWishListSaveRequestDto(Long memberId) {
		return WishListSaveRequestDto.builder()
			.memberId(memberId)
			.wishListName(wishListName)
			.build();
	}

}
