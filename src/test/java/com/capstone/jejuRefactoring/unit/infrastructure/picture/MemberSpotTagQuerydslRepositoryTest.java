package com.capstone.jejuRefactoring.unit.infrastructure.picture;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.Score;
import com.capstone.jejuRefactoring.domain.spot.Category;
import com.capstone.jejuRefactoring.domain.spot.PictureTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.dto.MemberSpotTageWithScoreDto;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotQuerydslRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.dto.TestDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
class MemberSpotTagQuerydslRepositoryTest {

	@Autowired
	MemberSpotTagQuerydslRepository memberSpotTagQuerydslRepository;

	@Autowired
	MemberJpaRepository memberJpaRepository;

	@Autowired
	SpotQuerydslRepository spotQuerydslRepository;

	@PersistenceContext
	EntityManager em;

	@Test
	public void 쿼리_테스트() throws Exception {
		//given

		Member member = createMember();
		Spot spot1 = createSpot("1");
		Spot spot2 = createSpot("2");
		Spot spot3 = createSpot("3");
		MemberSpotTag memberSpotTag1 = createMemberSpotTag(spot1, member);
		MemberSpotTag memberSpotTag2 = createMemberSpotTag(spot2, member);
		MemberSpotTag memberSpotTag3 = createMemberSpotTag(spot3, member);

		em.persist(member);
		em.persist(spot1);
		em.persist(spot2);
		em.persist(spot3);
		em.persist(memberSpotTag1);
		em.persist(memberSpotTag2);
		em.persist(memberSpotTag3);

		em.flush();
		em.clear();

		Member memberCo = memberJpaRepository.findOptionByEmail("email").get();

		List<MemberSpotTageWithScoreDto> memberSpotTageWithScoreAndSpot = memberSpotTagQuerydslRepository.findMemberSpotTageWithScoreAndSpot(
			memberCo.getId());
		log.info("memberSpotTageWithScoreAndSpot = {}", memberSpotTageWithScoreAndSpot);

	}

	@Test
	public void findWithCategoryScoreByLocationTest() throws Exception {
		//given
		Spot spot1 = createSpot("aaa");
		Spot spot2 = createSpot("aaa");
		Spot spot3 = createSpot("aaa");
		Score score1 = createScore(spot1, 1d);
		Score score2 = createScore(spot2, 2d);
		Score score3 = createScore(spot3, 3d);
		PictureTag pictureTag1 = createPictureTag(spot1, "111");
		PictureTag pictureTag2 = createPictureTag(spot2, "111");
		PictureTag pictureTag3 = createPictureTag(spot3, "111");

		em.persist(spot1);
		em.persist(spot2);
		em.persist(spot3);
		em.persist(score1);
		em.persist(score2);
		em.persist(score3);
		em.persist(pictureTag1);
		em.persist(pictureTag2);
		em.persist(pictureTag3);

		//when
		List<TestDto> test = spotQuerydslRepository.test(Category.VIEW);

		//then
		test.stream().forEach(testDto -> {
			System.out.println(testDto.getSpotId());
		});
	}

	private PictureTag createPictureTag(Spot spot1, String url) {
		return PictureTag.builder()
			.spot(spot1)
			.url(url)
			.build();
	}

	private Score createScore(Spot spot, Double score) {
		return Score.builder()
			.viewScore(score)
			.spot(spot)
			.build();
	}

	private MemberSpotTag createMemberSpotTag(Spot spot1, Member member) {
		return MemberSpotTag.builder()
			.spot(spot1)
			.member(member)
			.build();
	}

	private Spot createSpot(String address) {
		return Spot.builder()
			.address(address)
			.description("ss")
			.build();
	}

	private Member createMember() {
		return Member.builder()
			.email("email")
			.build();
	}
}