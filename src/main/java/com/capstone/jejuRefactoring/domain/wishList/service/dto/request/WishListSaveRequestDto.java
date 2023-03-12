package com.capstone.jejuRefactoring.domain.wishList.service.dto.request;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.wishList.WishList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WishListSaveRequestDto {

	private String wishListName;

	private Long memberId;

	public WishListSaveRequestDto(String wishListName, Long memberId) {
		this.wishListName = wishListName;
		this.memberId = memberId;
	}

	public WishList toEntity() {
		return WishList.builder()
			.member(Member.builder().id(memberId).build())
			.build();
	}

}
