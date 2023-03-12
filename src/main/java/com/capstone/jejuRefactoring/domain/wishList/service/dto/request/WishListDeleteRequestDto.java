package com.capstone.jejuRefactoring.domain.wishList.service.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WishListDeleteRequestDto {

	private Long wishListId;
	private Long memberId;

	public WishListDeleteRequestDto(Long wishListId, Long memberId) {
		this.wishListId = wishListId;
		this.memberId = memberId;
	}
}
