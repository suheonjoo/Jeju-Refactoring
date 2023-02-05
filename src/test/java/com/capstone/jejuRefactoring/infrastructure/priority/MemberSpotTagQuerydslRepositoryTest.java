package com.capstone.jejuRefactoring.infrastructure.priority;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.priority.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;

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

	@PersistenceContext
	EntityManager em;

	@Test
	public void 쿼리_테스트() throws Exception {
		//given

		Member member = Member.builder()
			.email("email")
			.build();

		Spot spot1 = Spot.builder()
			.address("1")
			.description("ss")
			.build();

		Spot spot2 = Spot.builder()
			.address("2")
			.description("ss")
			.build();

		Spot spot3 = Spot.builder()
			.address("3")
			.description("ss")
			.build();

		MemberSpotTag memberSpotTag1 = MemberSpotTag.builder()
			.spot(spot1)
			.member(member)
			.build();

		MemberSpotTag memberSpotTag2 = MemberSpotTag.builder()
			.spot(spot2)
			.member(member)
			.build();

		MemberSpotTag memberSpotTag3 = MemberSpotTag.builder()
			.spot(spot3)
			.member(member)
			.build();

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
}