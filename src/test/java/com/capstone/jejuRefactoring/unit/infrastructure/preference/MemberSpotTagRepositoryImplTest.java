package com.capstone.jejuRefactoring.unit.infrastructure.preference;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.domain.spot.dto.response.SpotPageResponse;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberSpotTagRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	MemberSpotTagRepositoryImpl memberSpotTagRepository;
	@Autowired
	MemberSpotTagJpaRepository memberSpotTagJpaRepository;
	@PersistenceContext
	EntityManager em;

	@Test
	public void memberSpotTags_벌크저장하기() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		List<Spot> spots = List.of(givenSpot(), givenSpot(), givenSpot(), givenSpot(), givenSpot());
		spotJpaRepository.saveAll(spots);
		List<MemberSpotTag> memberSpotTags = List.of(
			givenMemberSpotTag(spots.get(0).getId(), memberId), givenMemberSpotTag(spots.get(1).getId(), memberId),
			givenMemberSpotTag(spots.get(2).getId(), memberId), givenMemberSpotTag(spots.get(3).getId(), memberId),
			givenMemberSpotTag(spots.get(4).getId(), memberId));

		//when
		List<MemberSpotTag> target = memberSpotTagRepository.saveAll(memberSpotTags);

		//then
		Assertions.assertThat(target.size()).isEqualTo(5);
	}

	@Test
	public void 회원아이디와_관광지아이디들로_찾기() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		List<Spot> spots = List.of(givenSpot(), givenSpot(), givenSpot(), givenSpot(), givenSpot());
		spotJpaRepository.saveAll(spots);
		List<MemberSpotTag> memberSpotTags = List.of(
			givenMemberSpotTag(spots.get(0).getId(), memberId), givenMemberSpotTag(spots.get(1).getId(), memberId),
			givenMemberSpotTag(spots.get(2).getId(), memberId), givenMemberSpotTag(spots.get(3).getId(), memberId),
			givenMemberSpotTag(spots.get(4).getId(), memberId));
		List<MemberSpotTag> MemberSpotTags = memberSpotTagRepository.saveAll(memberSpotTags);

		//when
		List<MemberSpotTag> target = memberSpotTagRepository.findByMemberIdAndSpotIds(memberId,
			List.of(spots.get(0).getId(), spots.get(1).getId()));

		//then
		Assertions.assertThat(target.size()).isEqualTo(2);
	}

	// @Test
	// public void 회원아이디와_관광지아이디들로_찾기_성능튜닝() throws Exception {
	// 	//given
	// 	Long memberId = memberJpaRepository.save(givenMember()).getId();
	//
	// 	List<Spot> spots = new ArrayList<>();
	// 	for (int i = 0; i < 350; i++) {
	// 		spots.add(givenSpotWithName("관광지" + i));
	// 	}
	// 	List<Spot> spotList = spotJpaRepository.saveAllAndFlush(spots);
	// 	em.flush();
	// 	em.clear();
	//
	// 	List<MemberSpotTag> memberSpotTagList = new ArrayList<>();
	// 	for (int i = 0; i < 350; i++) {
	// 		memberSpotTagList.add(givenMemberSpotTag(spotList.get(i).getId(), memberId));
	// 	}
	// 	List<MemberSpotTag> MemberSpotTags = memberSpotTagRepository.saveAll(memberSpotTagList);
	//
	// 	long beforeTime = System.currentTimeMillis();
	// 	Slice<SpotPageResponse> old = memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId()
	// 	long afterTime = System.currentTimeMillis();
	// 	long secDiffTime = (afterTime - beforeTime);
	// 	log.info("시간차이(m) 평범 : {}", secDiffTime); //113
	// 	// log.info("old = {}",old.getContent());
	//
	// 	em.flush();
	// 	em.clear();
	//
	// 	long beforeTime1 = System.currentTimeMillis();
	// 	spotRepository.findPageBySpotName("관광", 0l,
	// 		pageRequest);
	// 	long afterTime1 = System.currentTimeMillis();
	// 	long secDiffTime1 = (afterTime1 - beforeTime1);
	// 	log.info("시간차이(m) 최적화 : {}", secDiffTime1); //28
	//
	// 	//when
	// 	List<MemberSpotTag> target = memberSpotTagRepository.findByMemberIdAndSpotIds(memberId,
	// 		List.of(spots.get(0).getId(), spots.get(1).getId()));
	//
	// 	//then
	// 	Assertions.assertThat(target.size()).isEqualTo(2);
	// }


	@Test
	public void 회원이_spotLike를_누른_경우() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();

		Spot spot = spotJpaRepository.save(givenSpot());
		MemberSpotTag memberSpotTag = memberSpotTagJpaRepository.save(givenMemberSpotTagWithSpotLike(spot.getId(), memberId,true));

		//when
		boolean target = memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId(spot.getId(), memberId);

		//then
		Assertions.assertThat(target).isTrue();
	}

	@Test
	public void 회원이_spotLike를_누르지_않은_경우() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		Spot spot = spotJpaRepository.save(givenSpot());
		MemberSpotTag memberSpotTag = memberSpotTagJpaRepository.save(givenMemberSpotTagWithSpotLike(spot.getId(), memberId,false));

		//when
		boolean target = memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId(spot.getId(), memberId);

		//then
		Assertions.assertThat(target).isFalse();
	}

	@Test
	public void spotlike_true로_만들기() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		Spot spot = spotJpaRepository.save(givenSpot());
		MemberSpotTag memberSpotTag = memberSpotTagJpaRepository.save(givenMemberSpotTagWithSpotLike(spot.getId(), memberId,false));

		//when
		memberSpotTagRepository.createSpotLikeByMemberIdAndSpotId(spot.getId(), memberId);
		boolean target = memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId(spot.getId(), memberId);

		//then
		Assertions.assertThat(target).isTrue();
	}

	@Test
	public void spotlike_false로_만들기() throws Exception {
		//given
		Long memberId = memberJpaRepository.save(givenMember()).getId();
		Spot spot = spotJpaRepository.save(givenSpot());
		MemberSpotTag memberSpotTag = memberSpotTagJpaRepository.save(givenMemberSpotTagWithSpotLike(spot.getId(), memberId,true));

		//when
		memberSpotTagRepository.deleteSpotLikeByMemberIdAndSpotId(spot.getId(), memberId);
		boolean target = memberSpotTagRepository.isSpotLikExistByMemberIdAndSpotId(spot.getId(), memberId);

		//then
		Assertions.assertThat(target).isFalse();
	}

}
