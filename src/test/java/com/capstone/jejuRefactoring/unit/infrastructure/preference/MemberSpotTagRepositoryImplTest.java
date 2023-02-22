package com.capstone.jejuRefactoring.unit.infrastructure.preference;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class MemberSpotTagRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	MemberSpotTagRepositoryImpl memberSpotTagRepository;
	@Autowired
	MemberSpotTagJpaRepository memberSpotTagJpaRepository;

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
