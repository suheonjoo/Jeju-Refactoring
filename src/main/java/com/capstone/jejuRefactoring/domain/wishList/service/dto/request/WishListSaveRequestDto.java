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
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WishListSaveRequestDto {

	private String wishListName;

	private Long memberId;

	public WishList toEntity() {
		return WishList.builder()
			.member(Member.builder().id(memberId).build())
			.build();
	}

}
