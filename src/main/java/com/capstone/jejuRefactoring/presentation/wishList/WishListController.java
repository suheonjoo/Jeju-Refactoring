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
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListDeleteRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSaveRequestDto;
import com.capstone.jejuRefactoring.domain.wishList.service.dto.request.WishListSpotTagDeleteRequestDto;
import com.capstone.jejuRefactoring.presentation.auth.LoginUser;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListDeleteRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListModifyRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListSaveRequest;
import com.capstone.jejuRefactoring.presentation.wishList.dto.request.WishListSpotTagDeleteRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/wishLists")
@RequiredArgsConstructor
@Slf4j
public class WishListController {

	private final WishListFacade wishListFacade;

	@PostMapping("/{wishListName}")
	public ResponseEntity<CommonResponse> saveWishList(@Validated @RequestBody WishListSaveRequest wishListSaveRequest,
		@PathVariable String wishListName , @LoginUser Member member) {
		wishListFacade.saveWishList(new WishListSaveRequestDto(wishListName, member.getId()));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@DeleteMapping("/{wishListId}")
	public ResponseEntity<CommonResponse> deleteWishList(
		@LoginUser Member member, @PathVariable final Long wishListId) {
		wishListFacade.deleteWishList(new WishListDeleteRequestDto(wishListId, member.getId()));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@DeleteMapping("/{wishListId}/{spotId}")
	public ResponseEntity<CommonResponse> deleteWishListSpotTagInWishList(
		@LoginUser Member member, @PathVariable final Long wishListId, @PathVariable final Long spotId) {
		wishListFacade.deleteWishListSpotTagInWishList(
			new WishListSpotTagDeleteRequestDto(wishListId, spotId, member.getId()));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@PatchMapping("/{wishListId}")
	public ResponseEntity<CommonResponse> reviseWishListName(
		@Validated @RequestBody WishListModifyRequest wishListModifyRequest, @LoginUser Member member,
		@PathVariable final Long wishListId) {
		wishListFacade.reviseWishListName(wishListModifyRequest.toWishListModifyRequestDto(member.getId(), wishListId));
		return ResponseEntity.ok()
			.body(CommonResponse.success());
	}

	@GetMapping()
	public ResponseEntity<CommonResponse> showWishLists(@LoginUser Member member) {
		return ResponseEntity.ok()
			.body(CommonResponse.success(wishListFacade.showWishLists(member.getId())));
	}

	@GetMapping("/{wishListId}")
	public ResponseEntity<CommonResponse> showWishList(@LoginUser Member member, @PathVariable final Long wishListId) {
		log.info("wishListId = {}", wishListId);
		return ResponseEntity.ok()
			.body(CommonResponse.success(wishListFacade.showWishList(member.getId(), wishListId)));
	}

}
