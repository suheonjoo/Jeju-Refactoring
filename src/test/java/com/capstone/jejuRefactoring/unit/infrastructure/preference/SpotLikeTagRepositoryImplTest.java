package com.capstone.jejuRefactoring.unit.infrastructure.preference;

import static com.capstone.jejuRefactoring.support.helper.PreferenceGivenHelper.*;
import static com.capstone.jejuRefactoring.support.helper.SpotGivenHelper.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.capstone.jejuRefactoring.domain.preference.SpotLikeTag;
import com.capstone.jejuRefactoring.infrastructure.preference.SpotLikeTagJpaRepository;
import com.capstone.jejuRefactoring.infrastructure.preference.SpotLikeTagRepositoryImpl;
import com.capstone.jejuRefactoring.infrastructure.spot.SpotJpaRepository;
import com.capstone.jejuRefactoring.support.QuerydslRepositoryTest;

public class SpotLikeTagRepositoryImplTest extends QuerydslRepositoryTest {

	@Autowired
	SpotLikeTagRepositoryImpl spotLikeTagRepository;
	@Autowired
	SpotJpaRepository spotJpaRepository;
	@Autowired
	SpotLikeTagJpaRepository spotLikeTagJpaRepository;


	@Test
	public void likeCount_증가하기() throws Exception{
	    //given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		spotLikeTagJpaRepository.save(givenSpotLikeTag(spotId,1));

	    //when
		spotLikeTagRepository.increaseLikeCount(spotId);
		SpotLikeTag target = spotLikeTagJpaRepository.findBySpotId(spotId).get();

		//then
		Assertions.assertThat(target.getLikeCount()).isEqualTo(2);
	}

	@Test
	public void likeCount_감소하기() throws Exception{
		//given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		spotLikeTagJpaRepository.save(givenSpotLikeTag(spotId,1));

		//when
		spotLikeTagRepository.decreaseLikeCount(spotId);
		SpotLikeTag target = spotLikeTagJpaRepository.findBySpotId(spotId).get();

		//then
		Assertions.assertThat(target.getLikeCount()).isEqualTo(0);
	}

	@Test
	public void 관광지아이디로_SpotLikeTag찾기() throws Exception{
	    //given
		Long spotId = spotJpaRepository.save(givenSpot()).getId();
		SpotLikeTag spotLikeTag = spotLikeTagJpaRepository.save(givenSpotLikeTag(spotId, 1));

		//when
		SpotLikeTag target = spotLikeTagRepository.findBySpotId(spotId).get();

		//then
		Assertions.assertThat(target).isEqualTo(spotLikeTag);
	}
}
