package com.capstone.jejuRefactoring.infrastructure.wishList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.repository.WishListRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class WishListRepositoryImpl implements WishListRepository {

	private final WishListJpaRepository wishListJpaRepository;

	@Override
	public WishList saveWishList(WishList wishList) {
		return wishListJpaRepository.save(wishList);
	}

	@Override
	public void updateWishListNameByWishListIdAndMemberId(String wishListName, Long wishListId, Long memberId) {
		wishListJpaRepository.updateWishListNameByWishListIdAndMemberId(wishListName, wishListId, memberId);
	}

	@Override
	public Optional<WishList> findByWishListNameAndMemberId(String wishListName, Long memberId) {
		return wishListJpaRepository.findByWishListNameAndMemberId(wishListName, memberId);
	}

	@Override
	public void deleteWishListByWishListIdAndMemberId(Long wishListId, Long memberId) {
		wishListJpaRepository.deleteByWishListIdAndMemberId(wishListId, memberId);
	}

	@Override
	public List<WishList> findByMemberIdWithWishListSpotTags(Long memberId) {
		return wishListJpaRepository.findByMemberIdWithWishListSpotTags(memberId);
	}

	@Override
	public List<WishList> findByMemberId(Long memberId) {
		return wishListJpaRepository.findByMemberId(memberId);
	}

}
