package com.capstone.jejuRefactoring.infrastructure.wishList;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capstone.jejuRefactoring.domain.wishList.WishList;

public interface WishListJpaRepository extends JpaRepository<WishList, Long> {

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query(value = "update WishList w set w.name = :wishListName where w.id = :wishListId and w.member.id = :memberId")
	void updateWishListNameByWishListIdAndMemberId(@Param("wishListName") String wishListName,
		@Param("wishListId") Long wishListId, @Param("memberId") Long memberId);

	@Query(value = "select w from WishList w where w.name = :wishListName and w.member.id = :memberId")
	Optional<WishList> findByWishListNameAndMemberId(@Param("wishListName") String wishListName,
		@Param("memberId") Long memberId);

	@Modifying(clearAutomatically = true)
	@Query(value = "delete from WishList w where w.id = :wishListId and w.member.id = :memberId")
	void deleteByWishListIdAndMemberId(@Param("wishListId") Long wishListId, @Param("memberId") Long memberId);

	@Query(value = "select w from WishList w join fetch w.wishListSpotTages where w.member.id = :memberId ")
	List<WishList> findByMemberIdWithWishListSpotTags(@Param("memberId") Long memberId);

	@Query(value = "select w from WishList w where w.member.id = : memberId")
	List<WishList> findByMemberId(@Param("memberId") Long memberId);

}
