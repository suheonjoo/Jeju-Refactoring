package com.capstone.jejuRefactoring.unit.infrastructure.preference;

import static com.capstone.jejuRefactoring.support.helper.MemberGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.capstone.jejuRefactoring.application.preference.PreferenceFacade;
import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.domain.spot.Spot;
import com.capstone.jejuRefactoring.infrastructure.auth.respository.MemberJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.MemberSpotTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.SpotLikeTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;

import lombok.extern.slf4j.Slf4j;

@Disabled
@SpringBootTest
@Slf4j
public class ConcurrencySpotLikeTest {

	@Autowired
	PreferenceFacade preferenceFacade;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	SpotLikeTagJpaRepository spotLikeTagJpaRepository;
	@Autowired
	MemberSpotTagJpaRepository memberSpotTagJpaRepository;
	@Autowired
	MemberJpaRepository memberJpaRepository;


	@Test
	public void 관광지_좋아요_동시성_테스트() throws Exception {
		//given
		memberJpaRepository.saveAll(List.of(givenMember(), givenMember()));
		Spot spot = spotJpaRepository.save(givenSpot());
		SpotLikeTag spotLikeTag = spotLikeTagJpaRepository.save(givenSpotLikeTag(spot.getId(), 0));
		memberSpotTagJpaRepository.save(givenMemberSpotTag(spot.getId(), 1l));
		memberSpotTagJpaRepository.save(givenMemberSpotTag(spot.getId(), 2l));

		//when
		int threadCount = 10000;
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					preferenceFacade.flipSpotLike(spot.getId(), 1l);
					preferenceFacade.flipSpotLike(spot.getId(), 2l);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		//then
		SpotLikeTag target = spotLikeTagJpaRepository.findById(spotLikeTag.getId()).get();
		assertEquals(0, target.getLikeCount());

	}

	@Test
	public void 좋아요_클릭_테스트() throws Exception {
		//given
		memberJpaRepository.saveAll(List.of(givenMember(), givenMember()));

		Spot spot = spotJpaRepository.save(givenSpot());
		SpotLikeTag spotLikeTag = spotLikeTagJpaRepository.save(givenSpotLikeTag(spot.getId(), 0));
		memberSpotTagJpaRepository.save(givenMemberSpotTag(spot.getId(), 1l));
		memberSpotTagJpaRepository.save(givenMemberSpotTag(spot.getId(), 2l));

		//when
		preferenceFacade.flipSpotLike(spot.getId(), 1l);
		preferenceFacade.flipSpotLike(spot.getId(), 1l);

		SpotLikeTag target = spotLikeTagJpaRepository.findById(spotLikeTag.getId()).get();

		//then
		assertEquals(0, target.getLikeCount());
	}

}
