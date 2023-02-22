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
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.wishList.WishListSpotTagRepositoryImpl;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WishListSpotTagRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	WishListSpotTagJpaRepository wishListSpotTagJpaRepository;
	@Autowired
	WishListSpotTagRepositoryImpl wishListSpotTagRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	WishListJpaRepository wishListJpaRepository;
	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Test
	public void wishListId로_spotId들을_찾아내기() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		WishList wishList = wishListJpaRepository.save(givenWishList(memberId));
		List<Spot> spots = spotJpaRepository.saveAll(List.of(givenSpot(), givenSpot(), givenSpot()));
		wishListSpotTagJpaRepository.saveAll(
			List.of(givenWishListSpotTag(spots.get(0).getId(), wishList.getId()),
				givenWishListSpotTag(spots.get(1).getId(), wishList.getId()),
				givenWishListSpotTag(spots.get(2).getId(), wishList.getId()))
		);

		//when
		List<Long> target = wishListSpotTagRepository.findSpotIdsByWishListId(wishList.getId());

		//then
		Assertions.assertThat(target.size()).isEqualTo(3);
	}


	@Test
	public void wishLIstId들로_찾아내기() throws Exception {
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
		List<WishListSpotTag> target = wishListSpotTagRepository.findByWishListIds(
			List.of(wishLists.get(0).getId(), wishLists.get(1).getId()));

		//then
		Assertions.assertThat(target.size()).isEqualTo(3);
	}

	@Test
	public void spotId와_wishListId로_삭제하기() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		List<WishList> wishLists = wishListJpaRepository.saveAll(List.of(givenWishList(memberId), givenWishList(memberId)));
		List<Spot> spots = spotJpaRepository.saveAll(List.of(givenSpot(), givenSpot(), givenSpot()));
		List<WishListSpotTag> wishListSpotTags = wishListSpotTagJpaRepository.saveAll(
			List.of(givenWishListSpotTag(spots.get(0).getId(), wishLists.get(0).getId()),
				givenWishListSpotTag(spots.get(1).getId(), wishLists.get(1).getId()),
				givenWishListSpotTag(spots.get(2).getId(), wishLists.get(1).getId()))
		);

		//when
		wishListSpotTagRepository.deleteByWishListIdAndSpotId(wishLists.get(1).getId(), spots.get(2).getId());
		Optional<WishListSpotTag> target = wishListSpotTagJpaRepository.findById(wishListSpotTags.get(2).getId());

		//then
		Assertions.assertThat(target).isEmpty();
	}

}
