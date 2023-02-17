package com.capstone.jejuRefactoring.domain.spot.dto.response;

import java.util.List;

import com.capstone.jejuRefactoring.domain.wishList.service.dto.response.WishListResponseDto;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.PictureTagUrlDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotPictureTagForWishListResponseDto {

	private Long wishListId;

	private String wishListName;

	private List<PictureTagUrlDto> pictureTagUrlDtos;

	public static SpotPictureTagForWishListResponseDto of(WishListResponseDto wishListResponseDto,
		List<PictureTagUrlDto> pictureTagUrlDtos) {
		return SpotPictureTagForWishListResponseDto.builder()
			.wishListId(wishListResponseDto.getWishListId())
			.wishListName(wishListResponseDto.getWishListName())
			.pictureTagUrlDtos(pictureTagUrlDtos)
			.build();
	}

}
