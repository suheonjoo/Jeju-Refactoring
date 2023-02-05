package com.capstone.jejuRefactoring.presentation.wishList;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.jejuRefactoring.application.wishList.WishListFacade;
import com.capstone.jejuRefactoring.common.exception.CommonResponse;
import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.presentation.auth.LoginUser;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListDeleteRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListModifyRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListSaveRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class WishListController {

	private final WishListFacade wishListFacade;

	@PostMapping("/{memberId}/wishLists/")
	public ResponseEntity<CommonResponse> saveWishList(@Validated @RequestBody WishListSaveRequest wishListSaveRequest,
		@LoginUser Member member) {
		wishListFacade.saveWishList(wishListSaveRequest.toWishListSaveRequestDto(member.getId()));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@DeleteMapping("/{memberId}/wishLists/{wishListId}")
	public ResponseEntity<CommonResponse> deleteWishList(
		@Validated @RequestBody WishListDeleteRequest wishListDeleteRequest, @LoginUser Member member,
		@PathVariable final Long wishListId) {
		wishListFacade.deleteWishList(wishListDeleteRequest.toWishListDeleteRequestDto(member.getId()));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@PatchMapping("/{memberId}/wishLists/{wishListId}")
	public ResponseEntity<CommonResponse> reviseWishListName(
		@Validated @RequestBody WishListModifyRequest wishListModifyRequest, @LoginUser Member member,
		@PathVariable final Long wishListId) {
		wishListFacade.reviseWishListName(wishListModifyRequest.toWishListModifyRequestDto(member.getId(), wishListId));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@GetMapping("/{memberId}/wishLists")
	public ResponseEntity<CommonResponse> showWishLists(@PathVariable final Long spotId, @LoginUser Member member,
		Pageable pageable) {
		wishListFacade.showWishLists(member.getId());
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@GetMapping("/{memberId}/wishLists/{wishListId}")
	public ResponseEntity<CommonResponse> showWishList(@PathVariable final Long memberId,
		@PathVariable final Long wishListId) {
		wishListFacade.showWishList(memberId, wishListId);
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

}
