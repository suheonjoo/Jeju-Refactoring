package com.capstone.jejuRefactoring.unit.ddl;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.picture.Picture;
import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.priority.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.review.Review;
import com.capstone.jejuRefactoring.domain.spot.Location;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.wishList.WishList;
import com.capstone.jejuRefactoring.domain.wishList.WishListSpotTag;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class DDLTest {

	@PersistenceContext
	EntityManager em;

	@Test
	public void spot_테스트() throws Exception{
	    //given
		Member member = getMember();

		Spot spot = getSpot();

		em.persist(member);
		em.persist(spot);

		//when
		Member member1 = em.createQuery("select m from Member m where m.email = :email", Member.class)
			.setParameter("email", "su@naver.com")
			.getResultList().get(0);

		Spot spot1 = em.createQuery("select s from Spot s where s.name = :name", Spot.class)
			.setParameter("name", "관광지1")
			.getResultList().get(0);

		WishList wishList = getWishList(member1);
		WishListSpotTag wishListSpotTag = getWishListSpotTag(spot1, wishList);
		MemberSpotTag memberSpotTag = getMemberSpotTag(member1, spot1);
		SpotLikeTag spotLikeTag = getSpotLikeTag(spot1);
		PictureTag pictureTag = getPictureTag(spot1);
		Picture picture = getPicture(spot1);
		Review review = getReview(spot1);
		em.persist(wishList);
		em.persist(wishListSpotTag);
		em.persist(memberSpotTag);
		em.persist(spotLikeTag);
		em.persist(pictureTag);
		em.persist(picture);
		em.persist(review);

		WishList wishList1 = em.createQuery("select w from WishList w where w.name = :name", WishList.class)
			.setParameter("name", "위시리스트1")
			.getResultList().get(0);
		log.info("wishList1 = {}", wishList1);

		WishListSpotTag wishListSpotTag1 = em.createQuery(
				"select wst from WishListSpotTag wst where wst.spot.id =:spotId and wst.wishList.id = :wishListId",
				WishListSpotTag.class)
			.setParameter("spotId", spot1.getId())
			.setParameter("wishListId", wishList1.getId())
			.getResultList().get(0);
		log.info("wishListSpotTag1 = {}", wishListSpotTag1);

		MemberSpotTag memberSpotTag1 = em.createQuery(
				"select mst from MemberSpotTag mst where mst.spot.id = :spotId and mst.member.id = :memberId",
				MemberSpotTag.class)
			.setParameter("spotId", spot1.getId())
			.setParameter("memberId", member1.getId())
			.getResultList().get(0);
		log.info("memberSpotTag1 = {}", memberSpotTag1);

		SpotLikeTag spotLikeTag1 = em.createQuery("select st from SpotLikeTag st where st.spot.id = :spotId",
				SpotLikeTag.class)
			.setParameter("spotId", spot1.getId())
			.getResultList().get(0);
		log.info("spotLikeTag1 = {}", spotLikeTag1);

		PictureTag pictureTag1 = em.createQuery("select pt from PictureTag pt where pt.spot.id = :spotId", PictureTag.class)
			.setParameter("spotId", spot1.getId())
			.getResultList().get(0);
		log.info("pictureTag1 = {}", pictureTag1);

		Picture picture1 = em.createQuery("select p from Picture p where p.spot.id = :spotId", Picture.class)
			.setParameter("spotId", spot1.getId())
			.getResultList().get(0);
		log.info("picture1 = {}", picture1);

		Review review1 = em.createQuery("select r from Review r where r.spot.id = :spotId", Review.class)
			.setParameter("spotId", spot1.getId())
			.getResultList().get(0);
		log.info("review1 = {}", review1);

		//then
	}

	private Review getReview(Spot spot1) {
		return Review.builder()
			.spot(spot1)
			.content("content~~")
			.build();
	}

	private Picture getPicture(Spot spot1) {
		return Picture.builder()
			.spot(spot1)
			.url("url")
			.build();
	}

	private PictureTag getPictureTag(Spot spot1) {
		return PictureTag.builder()
			.url("urltag")
			.spot(spot1)
			.build();
	}

	private MemberSpotTag getMemberSpotTag(Member member1, Spot spot1) {
		return MemberSpotTag.builder()
			.IsLikeExist(false)
			.spot(spot1)
			.personalScore(4d)
			.member(member1)
			.build();
	}

	private SpotLikeTag getSpotLikeTag(Spot spot1) {
		return SpotLikeTag.builder()
			.likeCount(0)
			.spot(spot1)
			.build();
	}

	private WishListSpotTag getWishListSpotTag(Spot spot1, WishList wishList) {
		return WishListSpotTag.builder()
			.spot(spot1)
			.wishList(wishList)
			.build();
	}

	private WishList getWishList(Member member1) {
		return WishList.builder()
			.member(member1)
			.name("위시리스트1")
			.build();
	}

	private Spot getSpot() {
		return Spot.builder()
			.description("설명")
			.name("관광지1")
			.address("주소")
			.location(Location.Aewol_eup)
			.likeCount(0)
			.build();
	}

	private Member getMember() {
		return Member.builder()
			.username("soo")
			.email("su@naver.com")
			.build();
	}

}
