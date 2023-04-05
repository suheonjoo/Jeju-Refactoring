package com.capstone.jejuRefactoring.unit.infrastructure.preference;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.jejuRefactoring.domain.auth.Member;
import com.capstone.jejuRefactoring.domain.preference.MemberSpotTag;
import com.capstone.jejuRefactoring.domain.preference.repository.MemberSpotTagRepository;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;

import lombok.extern.slf4j.Slf4j;

@Disabled
@Slf4j
@SpringBootTest
public class SaveAllOptimizeTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	MemberSpotTagRepository memberSpotTagRepository;

	@Test
	public void springDataJPASaveALl_테스트() throws Exception{
	    //given
		Member member = memberJpaRepository.save(givenMember());

		List<Spot> spots = new ArrayList<>();
		for (int i = 0; i < 360; i++) {
			spots.add(givenSpot());
		}
		List<Spot> savedSpots = spotJpaRepository.saveAll(spots);

		List<MemberSpotTag> memberSpotTags = new ArrayList<>();
		for (int i = 0; i < 360; i++) {
			memberSpotTags.add(givenMemberSpotTag(savedSpots.get(i).getId(), member.getId()));
		}

		//when
		long beforeTime = System.currentTimeMillis();
		memberSpotTagRepository.saveAll(memberSpotTags);
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime);
		log.info("시간차이(m) 스프링데이터 jpa - saveAll : {}", secDiffTime); // 73

	    //then
	}


	@Test
	public void jdbcSaveAll_테스트() throws Exception{
		//given
		Member member = memberJpaRepository.save(givenMember());

		List<Spot> spots = new ArrayList<>();
		for (int i = 0; i < 360; i++) {
			spots.add(givenSpot());
		}
		List<Spot> savedSpots = spotJpaRepository.saveAll(spots);

		List<MemberSpotTag> memberSpotTags = new ArrayList<>();
		for (int i = 0; i < 360; i++) {
			memberSpotTags.add(givenMemberSpotTag(savedSpots.get(i).getId(), member.getId()));
		}


		long beforeTime = System.currentTimeMillis();
		memberSpotTagRepository.saveAll(memberSpotTags);
		long afterTime = System.currentTimeMillis();
		long secDiffTime = (afterTime - beforeTime);
		log.info("시간차이(m) jdbc - saveAll : {}", secDiffTime); //25

	    //then
	}

}
