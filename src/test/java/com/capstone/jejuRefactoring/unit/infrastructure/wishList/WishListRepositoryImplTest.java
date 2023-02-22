package com.capstone.jejuRefactoring.unit.infrastructure.wishList;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.WishListGivenHelper.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagRepositoryImpl;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class WishListRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	WishListSpotTagJpaRepository wishListSpotTagJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	WishListJpaRepository wishListJpaRepository;
	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	WishListRepositoryImpl wishListRepository;

	@Test
	public void wishList_저장하기() throws Exception{
	    //given
		Long memberId = memberJpaRepository.save(givenMember()).getId();

	    //when
		WishList wishList = givenWishList(memberId);
		WishList target = wishListRepository.saveWishList(wishList);

	    //then
		Assertions.assertThat(wishList).isEqualTo(target);
	}

	@Test
	public void 위시리시트이름_변경하기() throws Exception{
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		WishList wishList = wishListRepository.saveWishList(givenWishList(memberId));

		//when
		wishListRepository.updateWishListNameByWishListIdAndMemberId("change",wishList.getId(), memberId);
		WishList target = wishListJpaRepository.findById(wishList.getId()).get();

		//then
		Assertions.assertThat(target.getName()).isEqualTo("change");
	}

	@Test
	public void 위시리스트이름과_memberId로_위시리스트_찾기() throws Exception{
	    //given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		WishList wishList = wishListRepository.saveWishList(givenWishList(memberId));

	    //when
		Optional<WishList> target = wishListRepository.findByWishListNameAndMemberId(
			wishList.getName(), memberId);

		//then
		Assertions.assertThat(target.get()).isEqualTo(wishList);
	}

	@Test
	public void 위시리스트_삭제하기() throws Exception{
	    //given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		WishList wishList = wishListRepository.saveWishList(givenWishList(memberId));

	    //when
		wishListRepository.deleteWishListByWishListIdAndMemberId(wishList.getId(), memberId);
		Optional<WishList> target = wishListJpaRepository.findById(wishList.getId());

		//then
		Assertions.assertThat(target).isEmpty();
	}

	@Test
	public void 한_회원의_위시리스트에서_wishlistSpotTag패치조인하기() throws Exception{
	    //given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		List<WishList> wishLists = wishListJpaRepository.saveAll(List.of(givenWishList(memberId), givenWishList(memberId)));
		List<Spot> spots = spotJpaRepository.saveAll(List.of(givenSpot(), givenSpot(), givenSpot()));
		wishListSpotTagJpaRepository.saveAll(
			List.of(givenWishListSpotTag(spots.get(0).getId(), wishLists.get(0).getId()),
				givenWishListSpotTag(spots.get(1).getId(), wishLists.get(1).getId()),
				givenWishListSpotTag(spots.get(2).getId(), wishLists.get(1).getId()))
		);

	    //when
		List<WishList> target = wishListRepository.findByMemberIdWithWishListSpotTags(memberId);

		//then
		Assertions.assertThat(target.size()).isEqualTo(2);
	}

	@Test
	public void memberId로_위시리시트들_찾기() throws Exception{
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		List<WishList> wishLists = wishListJpaRepository.saveAll(List.of(givenWishList(memberId), givenWishList(memberId)));
		List<Spot> spots = spotJpaRepository.saveAll(List.of(givenSpot(), givenSpot(), givenSpot()));
		wishListSpotTagJpaRepository.saveAll(
			List.of(givenWishListSpotTag(spots.get(0).getId(), wishLists.get(0).getId()),
				givenWishListSpotTag(spots.get(1).getId(), wishLists.get(1).getId()),
				givenWishListSpotTag(spots.get(2).getId(), wishLists.get(1).getId()))
		);

		//when
		List<WishList> target = wishListRepository.findByMemberId(memberId);

		//then
		Assertions.assertThat(target.size()).isEqualTo(2);
	}

}
