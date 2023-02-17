package com.capstone.jejuRefactoring.domain.wishList.service.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishListDeleteRequestDto {

	private Long wishListId;
	private Long spotId;
	private Long memberId;

}
