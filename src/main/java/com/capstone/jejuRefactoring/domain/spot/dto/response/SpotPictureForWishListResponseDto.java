package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListResponseDto;
import com.capstone.jejuRefactoring.infrastructure.spot.PictureUrlDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotPictureForWishListResponseDto {

	private Long wishListId;

	private String wishListName;

	private List<PictureUrlDto> pictureUrlDtos;

	public static SpotPictureForWishListResponseDto of(WishListResponseDto wishListResponseDto, List<PictureUrlDto> pictureUrlDtos) {
		return SpotPictureForWishListResponseDto.builder()
			.wishListId(wishListResponseDto.getWishListId())
			.wishListName(wishListResponseDto.getWishListName())
			.pictureUrlDtos(pictureUrlDtos)
			.build();
	}


}
