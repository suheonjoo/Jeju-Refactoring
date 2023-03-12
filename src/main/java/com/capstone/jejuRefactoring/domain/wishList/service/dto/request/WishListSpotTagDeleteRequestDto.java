package com.capstone.jejuRefactoring.domain.wishList.service.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WishListSpotTagDeleteRequestDto {
	private Long wishListId;
	private Long spotId;
	private Long memberId;

	public WishListSpotTagDeleteRequestDto(Long wishListId, Long spotId, Long memberId) {
		this.wishListId = wishListId;
		this.spotId = spotId;
		this.memberId = memberId;
	}
}